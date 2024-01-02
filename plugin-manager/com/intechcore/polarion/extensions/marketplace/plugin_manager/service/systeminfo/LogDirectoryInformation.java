package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;

public class LogDirectoryInformation {
   private static final String LOGDIR = "logDir";
   private final String logDirPath;
   private final long logDirectorySizeKB;
   private final Map<String, Long> subDirectorySizesKB;

   public LogDirectoryInformation() {
      File mainLogDir = new File(System.getProperty("logDir"));
      this.logDirPath = mainLogDir.getParent();
      File logsDir = new File(this.logDirPath);
      this.subDirectorySizesKB = new HashMap();
      Arrays.stream(logsDir.listFiles()).filter(File::isDirectory).forEach((file) -> {
         this.subDirectorySizesKB.put(file.getName(), FileUtils.sizeOfDirectory(file) / 1024L);
      });
      this.logDirectorySizeKB = FileUtils.sizeOfDirectory(logsDir) / 1024L;
   }

   public String getLogDirPath() {
      return this.logDirPath;
   }

   public long getLogDirectorySizeKB() {
      return this.logDirectorySizeKB;
   }

   public Map<String, Long> getSubDirectorySizesKB() {
      return this.subDirectorySizesKB;
   }
}
