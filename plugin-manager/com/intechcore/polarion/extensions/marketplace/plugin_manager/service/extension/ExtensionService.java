package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.extension;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.util.PolarionUtils;
import com.polarion.core.util.logging.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtensionService {
   public static final String POLARION_EXTENSIONS_DIR = "extensions";
   public static final String ECLIPSE = "eclipse";
   public static final String PLUGINS = "plugins";
   public static final String JAR_EXTENSION = ".jar";
   private static final Logger logger = Logger.getLogger(ExtensionService.class);
   private static final int MAX_SEARCH_DEPTH = 2;

   @NotNull
   public static ExtensionService getInstance() {
      return ExtensionService.InstanceHolder.instance;
   }

   @NotNull
   private static HashMap<String, String> getAttributes(@NotNull File file) {
      try {
         JarFile jarFile = new JarFile(file);
         Attributes attributes = jarFile.getManifest().getMainAttributes();
         HashMap<String, String> result = new HashMap();
         attributes.entrySet().forEach((entry) -> {
            result.put(entry.getKey().toString(), entry.getValue().toString());
         });
         return result;
      } catch (IOException var4) {
         logger.error("Error while getting attributes", var4);
         throw new RuntimeException(var4);
      }
   }

   private static boolean checkExtensionDir(@NotNull File dir) {
      if (!dir.isDirectory()) {
         return false;
      } else {
         File eclipseDir = new File(dir, "eclipse");
         if (!eclipseDir.isDirectory()) {
            return false;
         } else {
            File pluginsDir = new File(eclipseDir, "plugins");
            return pluginsDir.isDirectory();
         }
      }
   }

   @NotNull
   private static Optional<File> getJarFile(@NotNull String directory) {
      File extensionDir = new File(directory);
      if (!checkExtensionDir(extensionDir)) {
         return Optional.empty();
      } else {
         File eclipseDir = new File(extensionDir, "eclipse");
         File pluginsDir = new File(eclipseDir, "plugins");

         try {
            Optional<Path> optionalPath = ((List)Files.find(Paths.get(pluginsDir.getAbsolutePath()), 2, (path, basicFileAttributes) -> {
               return basicFileAttributes.isRegularFile() && !path.toString().toLowerCase().contains("/lib/") && path.toString().toLowerCase().endsWith(".jar");
            }, new FileVisitOption[0]).collect(Collectors.toList())).stream().findAny();
            return optionalPath.map(Path::toFile);
         } catch (IOException var5) {
            logger.error("Error while getting jar file", var5);
            return Optional.empty();
         }
      }
   }

   @Nullable
   private static InstalledExtension map(@NotNull String extensionPath) {
      Optional<File> jar = getJarFile(extensionPath);
      if (jar.isPresent()) {
         Map<String, String> attributes = getAttributes((File)jar.get());
         return InstalledExtension.builder().fileName(((File)jar.get()).getName()).folderName((new File(extensionPath)).getName()).bundleName((String)attributes.get("Bundle-Name")).bundleVersion((String)attributes.get("Bundle-Version")).attributes(attributes).build();
      } else {
         return null;
      }
   }

   @NotNull
   public List<InstalledExtension> getInstalledExtensions() {
      File extensionsDir = new File(PolarionUtils.getPolarionHomePath(), "extensions");
      return !extensionsDir.isDirectory() ? List.of() : (List)Arrays.stream(extensionsDir.listFiles()).filter(ExtensionService::checkExtensionDir).map((file) -> {
         return map(file.getAbsolutePath());
      }).filter(Objects::nonNull).collect(Collectors.toList());
   }

   private ExtensionService() {
   }

   private static class InstanceHolder {
      public static final ExtensionService instance = new ExtensionService();
   }
}
