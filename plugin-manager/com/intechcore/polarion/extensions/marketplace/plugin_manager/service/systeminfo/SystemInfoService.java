package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import org.jetbrains.annotations.NotNull;
import oshi.SystemInfo;

public class SystemInfoService {
   @NotNull
   public static SystemInfoService getInstance() {
      return SystemInfoService.InstanceHolder.instance;
   }

   @NotNull
   public SystemInformation getSystemInformation() {
      return new SystemInformation(new SystemInfo());
   }

   private static class InstanceHolder {
      public static final SystemInfoService instance = new SystemInfoService();
   }
}
