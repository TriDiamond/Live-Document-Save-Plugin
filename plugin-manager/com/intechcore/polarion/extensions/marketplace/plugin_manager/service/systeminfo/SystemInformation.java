package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import com.polarion.core.config.Configuration;
import org.jetbrains.annotations.NotNull;
import oshi.PlatformEnum;
import oshi.SystemInfo;

public class SystemInformation {
   private final PlatformEnum currentPlatform = SystemInfo.getCurrentPlatform();
   private final String operatingSystem;
   private final String polarionVersion;
   private final CpuInformation cpuInformation;
   private final FileSystemInformation fileSystemInformation;
   private final ProcessInformation processInformation;
   private final MemoryInformation memoryInformation;
   private final SystemPropertiesInformation systemPropertiesInformation;
   private final LogDirectoryInformation directoryInformation;
   private final PolarionPropertiesInformation polarionPropertiesInformation;
   private final ConfigIniInformation configIniInformation;
   private final PolarionIniInformation polarionIniInformation;

   public SystemInformation(@NotNull SystemInfo systemInfo) {
      this.operatingSystem = String.format("%s %s %s", systemInfo.getOperatingSystem().getManufacturer(), systemInfo.getOperatingSystem().getFamily(), systemInfo.getOperatingSystem().getVersionInfo());
      this.polarionVersion = Configuration.getInstance().getProduct().version();
      this.fileSystemInformation = new FileSystemInformation(systemInfo.getOperatingSystem().getFileSystem(), this.currentPlatform);
      this.processInformation = new ProcessInformation(systemInfo.getOperatingSystem().getCurrentProcess());
      this.cpuInformation = new CpuInformation(systemInfo.getHardware().getProcessor());
      this.memoryInformation = new MemoryInformation(systemInfo.getHardware().getMemory());
      this.systemPropertiesInformation = new SystemPropertiesInformation();
      this.polarionPropertiesInformation = new PolarionPropertiesInformation();
      this.directoryInformation = new LogDirectoryInformation();
      this.configIniInformation = new ConfigIniInformation(this.currentPlatform);
      this.polarionIniInformation = new PolarionIniInformation(this.currentPlatform);
   }

   public PlatformEnum getCurrentPlatform() {
      return this.currentPlatform;
   }

   public String getOperatingSystem() {
      return this.operatingSystem;
   }

   public String getPolarionVersion() {
      return this.polarionVersion;
   }

   public CpuInformation getCpuInformation() {
      return this.cpuInformation;
   }

   public FileSystemInformation getFileSystemInformation() {
      return this.fileSystemInformation;
   }

   public ProcessInformation getProcessInformation() {
      return this.processInformation;
   }

   public MemoryInformation getMemoryInformation() {
      return this.memoryInformation;
   }

   public SystemPropertiesInformation getSystemPropertiesInformation() {
      return this.systemPropertiesInformation;
   }

   public LogDirectoryInformation getDirectoryInformation() {
      return this.directoryInformation;
   }

   public PolarionPropertiesInformation getPolarionPropertiesInformation() {
      return this.polarionPropertiesInformation;
   }

   public ConfigIniInformation getConfigIniInformation() {
      return this.configIniInformation;
   }

   public PolarionIniInformation getPolarionIniInformation() {
      return this.polarionIniInformation;
   }
}
