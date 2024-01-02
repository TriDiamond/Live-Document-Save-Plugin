package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.extension;

import java.util.Map;

public class InstalledExtension {
   private String fileName;
   private String folderName;
   private String bundleName;
   private String bundleVersion;
   private Map<String, String> attributes;

   InstalledExtension(String fileName, String folderName, String bundleName, String bundleVersion, Map<String, String> attributes) {
      this.fileName = fileName;
      this.folderName = folderName;
      this.bundleName = bundleName;
      this.bundleVersion = bundleVersion;
      this.attributes = attributes;
   }

   public static InstalledExtension.InstalledExtensionBuilder builder() {
      return new InstalledExtension.InstalledExtensionBuilder();
   }

   public String getFileName() {
      return this.fileName;
   }

   public String getFolderName() {
      return this.folderName;
   }

   public String getBundleName() {
      return this.bundleName;
   }

   public String getBundleVersion() {
      return this.bundleVersion;
   }

   public Map<String, String> getAttributes() {
      return this.attributes;
   }

   public static class InstalledExtensionBuilder {
      private String fileName;
      private String folderName;
      private String bundleName;
      private String bundleVersion;
      private Map<String, String> attributes;

      InstalledExtensionBuilder() {
      }

      public InstalledExtension.InstalledExtensionBuilder fileName(String fileName) {
         this.fileName = fileName;
         return this;
      }

      public InstalledExtension.InstalledExtensionBuilder folderName(String folderName) {
         this.folderName = folderName;
         return this;
      }

      public InstalledExtension.InstalledExtensionBuilder bundleName(String bundleName) {
         this.bundleName = bundleName;
         return this;
      }

      public InstalledExtension.InstalledExtensionBuilder bundleVersion(String bundleVersion) {
         this.bundleVersion = bundleVersion;
         return this;
      }

      public InstalledExtension.InstalledExtensionBuilder attributes(Map<String, String> attributes) {
         this.attributes = attributes;
         return this;
      }

      public InstalledExtension build() {
         return new InstalledExtension(this.fileName, this.folderName, this.bundleName, this.bundleVersion, this.attributes);
      }

      public String toString() {
         return "InstalledExtension.InstalledExtensionBuilder(fileName=" + this.fileName + ", folderName=" + this.folderName + ", bundleName=" + this.bundleName + ", bundleVersion=" + this.bundleVersion + ", attributes=" + this.attributes + ")";
      }
   }
}
