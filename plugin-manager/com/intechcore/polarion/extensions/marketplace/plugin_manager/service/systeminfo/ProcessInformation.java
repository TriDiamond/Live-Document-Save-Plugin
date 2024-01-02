package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import org.jetbrains.annotations.NotNull;
import oshi.software.os.OSProcess;

public class ProcessInformation {
   private final int currentPID;
   private final long usedRamMB;
   private final long maxRamMB;
   private final int priority;
   private final double cpuLoad;

   public ProcessInformation(@NotNull OSProcess currentProcess) {
      this.currentPID = currentProcess.getProcessID();
      this.usedRamMB = currentProcess.getResidentSetSize() / 1048576L;
      this.maxRamMB = currentProcess.getVirtualSize() / 1048576L;
      this.priority = currentProcess.getPriority();
      this.cpuLoad = currentProcess.getProcessCpuLoadCumulative();
   }

   public int getCurrentPID() {
      return this.currentPID;
   }

   public long getUsedRamMB() {
      return this.usedRamMB;
   }

   public long getMaxRamMB() {
      return this.maxRamMB;
   }

   public int getPriority() {
      return this.priority;
   }

   public double getCpuLoad() {
      return this.cpuLoad;
   }
}
