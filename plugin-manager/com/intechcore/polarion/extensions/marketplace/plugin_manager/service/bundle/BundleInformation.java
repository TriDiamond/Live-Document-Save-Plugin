package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.bundle;

import java.util.Map;

public class BundleInformation {
   private long bundleId;
   private BundleState state;
   private String location;
   private String symbolicName;
   private String bundleVersion;
   private long lastModified;
   private BundleType bundleType;
   private Map<String, String> attributes;

   BundleInformation(long bundleId, BundleState state, String location, String symbolicName, String bundleVersion, long lastModified, BundleType bundleType, Map<String, String> attributes) {
      this.bundleId = bundleId;
      this.state = state;
      this.location = location;
      this.symbolicName = symbolicName;
      this.bundleVersion = bundleVersion;
      this.lastModified = lastModified;
      this.bundleType = bundleType;
      this.attributes = attributes;
   }

   public static BundleInformation.BundleInformationBuilder builder() {
      return new BundleInformation.BundleInformationBuilder();
   }

   public long getBundleId() {
      return this.bundleId;
   }

   public BundleState getState() {
      return this.state;
   }

   public String getLocation() {
      return this.location;
   }

   public String getSymbolicName() {
      return this.symbolicName;
   }

   public String getBundleVersion() {
      return this.bundleVersion;
   }

   public long getLastModified() {
      return this.lastModified;
   }

   public BundleType getBundleType() {
      return this.bundleType;
   }

   public Map<String, String> getAttributes() {
      return this.attributes;
   }

   public static class BundleInformationBuilder {
      private long bundleId;
      private BundleState state;
      private String location;
      private String symbolicName;
      private String bundleVersion;
      private long lastModified;
      private BundleType bundleType;
      private Map<String, String> attributes;

      BundleInformationBuilder() {
      }

      public BundleInformation.BundleInformationBuilder bundleId(long bundleId) {
         this.bundleId = bundleId;
         return this;
      }

      public BundleInformation.BundleInformationBuilder state(BundleState state) {
         this.state = state;
         return this;
      }

      public BundleInformation.BundleInformationBuilder location(String location) {
         this.location = location;
         return this;
      }

      public BundleInformation.BundleInformationBuilder symbolicName(String symbolicName) {
         this.symbolicName = symbolicName;
         return this;
      }

      public BundleInformation.BundleInformationBuilder bundleVersion(String bundleVersion) {
         this.bundleVersion = bundleVersion;
         return this;
      }

      public BundleInformation.BundleInformationBuilder lastModified(long lastModified) {
         this.lastModified = lastModified;
         return this;
      }

      public BundleInformation.BundleInformationBuilder bundleType(BundleType bundleType) {
         this.bundleType = bundleType;
         return this;
      }

      public BundleInformation.BundleInformationBuilder attributes(Map<String, String> attributes) {
         this.attributes = attributes;
         return this;
      }

      public BundleInformation build() {
         return new BundleInformation(this.bundleId, this.state, this.location, this.symbolicName, this.bundleVersion, this.lastModified, this.bundleType, this.attributes);
      }

      public String toString() {
         return "BundleInformation.BundleInformationBuilder(bundleId=" + this.bundleId + ", state=" + this.state + ", location=" + this.location + ", symbolicName=" + this.symbolicName + ", bundleVersion=" + this.bundleVersion + ", lastModified=" + this.lastModified + ", bundleType=" + this.bundleType + ", attributes=" + this.attributes + ")";
      }
   }
}
