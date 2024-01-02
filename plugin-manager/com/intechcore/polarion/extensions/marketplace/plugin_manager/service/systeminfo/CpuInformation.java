package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import org.jetbrains.annotations.NotNull;
import oshi.hardware.CentralProcessor;

public class CpuInformation {
   private final String cpuName;
   private final int physicalCpuCount;
   private final int logicalCpuCount;
   private final double cpuLoad;

   public CpuInformation(@NotNull CentralProcessor processor) {
      this.cpuName = processor.getProcessorIdentifier().getName();
      this.physicalCpuCount = processor.getPhysicalProcessorCount();
      this.logicalCpuCount = processor.getLogicalProcessorCount();
      this.cpuLoad = processor.getSystemCpuLoad(1000L);
   }

   public String getCpuName() {
      return this.cpuName;
   }

   public int getPhysicalCpuCount() {
      return this.physicalCpuCount;
   }

   public int getLogicalCpuCount() {
      return this.logicalCpuCount;
   }

   public double getCpuLoad() {
      return this.cpuLoad;
   }
}
