package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.util.PolarionUtils;
import com.polarion.core.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import oshi.PlatformEnum;

public class PolarionIniInformation {
   private static final Logger logger = Logger.getLogger(PolarionIniInformation.class);
   private static final String POLARION_INI = "polarion.ini";
   private static final String CONFIG_SH = "config.sh";
   private final String content;

   public PolarionIniInformation(PlatformEnum currentPlatform) {
      String content = null;

      try {
         File file = this.getFile(currentPlatform);
         content = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
         content = this.filterContent(content, currentPlatform);
      } catch (IOException var4) {
         logger.error("Error reading Config ini", var4);
      }

      this.content = content;
   }

   private String filterContent(String content, PlatformEnum currentPlatform) {
      if (PlatformEnum.WINDOWS.equals(currentPlatform)) {
         content = content.replaceAll("(?m)^\".*", "");
      }

      if (PlatformEnum.LINUX.equals(currentPlatform)) {
         content = content.replaceAll("(?m)^#.*", "");
      }

      return content;
   }

   @NotNull
   File getFile(PlatformEnum currentPlatform) {
      File file;
      switch(currentPlatform) {
      case WINDOWS:
         file = new File(PolarionUtils.getPolarionHomePath(), "polarion.ini");
         break;
      case LINUX:
         File polarionPropertiesFile = new File(PolarionUtils.getExternalPropertyPath());
         File configurationDir = polarionPropertiesFile.getParentFile();
         file = new File(configurationDir, "config.sh");
         break;
      default:
         throw new IllegalArgumentException("OS Not supported");
      }

      return file;
   }

   public String getContent() {
      return this.content;
   }
}
