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

public class ConfigIniInformation {
   private static final Logger logger = Logger.getLogger(ConfigIniInformation.class);
   private static final String CONFIGURATION = "configuration";
   private static final String CONFIG_INI = "config.ini";
   private final String content;

   public ConfigIniInformation(PlatformEnum currentPlatform) {
      String content = null;

      try {
         File file = this.getFile(currentPlatform);
         content = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
      } catch (IOException var4) {
         logger.error("Error reading Config ini", var4);
      }

      this.content = content;
   }

   @NotNull
   File getFile(PlatformEnum currentPlatform) {
      switch(currentPlatform) {
      case WINDOWS:
      case LINUX:
         File configDir = new File(PolarionUtils.getPolarionHomePath(), "configuration");
         File file = new File(configDir, "config.ini");
         return file;
      default:
         throw new IllegalArgumentException("OS Not supported");
      }
   }

   public String getContent() {
      return this.content;
   }
}
