package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.util.PolarionUtils;
import com.polarion.core.util.logging.Logger;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import oshi.PlatformEnum;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;

public class FileSystemInformation {
   private static final Logger logger = Logger.getLogger(FileSystemInformation.class);
   private final long totalSpaceMB;
   private final long freeSpaceMB;
   private final String mountPath;

   public FileSystemInformation(@NotNull FileSystem fileSystem, @NotNull PlatformEnum currentPlatform) {
      String homePath = PolarionUtils.getPolarionHomePath();
      if (PlatformEnum.WINDOWS == currentPlatform) {
         homePath = homePath.replaceFirst("/", "");
         homePath = homePath.replace("/", "\\");
      }

      Optional<OSFileStore> fileStore = Optional.empty();
      String mountPath = null;

      try {
         mountPath = mountOf(Path.of(homePath, new String[0])).toString();
         fileStore = fileSystem.getFileStores(true).stream().filter((osFileStore) -> {
            return mountPath.startsWith(osFileStore.getMount());
         }).findFirst();
      } catch (IOException var7) {
         logger.error("Error reading File System Information", var7);
      }

      this.mountPath = mountPath;
      if (fileStore.isPresent()) {
         this.totalSpaceMB = ((OSFileStore)fileStore.get()).getTotalSpace() / 1048576L;
         this.freeSpaceMB = ((OSFileStore)fileStore.get()).getFreeSpace() / 1048576L;
      } else {
         this.totalSpaceMB = 0L;
         this.freeSpaceMB = 0L;
         logger.error("Could not find File Store");
      }

   }

   private static Path mountOf(Path p) throws IOException {
      FileStore fs = Files.getFileStore(p);
      Path temp = p.toAbsolutePath();

      Path mountp;
      for(mountp = temp; (temp = temp.getParent()) != null && fs.equals(Files.getFileStore(temp)); mountp = temp) {
      }

      return mountp;
   }

   public long getTotalSpaceMB() {
      return this.totalSpaceMB;
   }

   public long getFreeSpaceMB() {
      return this.freeSpaceMB;
   }

   public String getMountPath() {
      return this.mountPath;
   }
}
