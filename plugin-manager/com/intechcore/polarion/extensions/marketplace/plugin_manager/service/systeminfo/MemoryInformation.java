package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import org.jetbrains.annotations.NotNull;
import oshi.hardware.GlobalMemory;

public class MemoryInformation {
   private final long totalMemoryMB;
   private final long availableMemoryMB;
   private final long totalSwapMB;
   private final long availableSwapMB;

   public MemoryInformation(@NotNull GlobalMemory memory) {
      this.totalMemoryMB = memory.getTotal() / 1048576L;
      this.availableMemoryMB = memory.getAvailable() / 1048576L;
      this.totalSwapMB = memory.getVirtualMemory().getSwapTotal() / 1048576L;
      this.availableSwapMB = (memory.getVirtualMemory().getSwapTotal() - memory.getVirtualMemory().getSwapUsed()) / 1048576L;
   }

   public long getTotalMemoryMB() {
      return this.totalMemoryMB;
   }

   public long getAvailableMemoryMB() {
      return this.availableMemoryMB;
   }

   public long getTotalSwapMB() {
      return this.totalSwapMB;
   }

   public long getAvailableSwapMB() {
      return this.availableSwapMB;
   }
}
