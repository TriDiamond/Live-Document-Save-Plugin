package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model;

public class Version {
   private String bundleName;
   private String bundleVendor;
   private String automaticModuleName;
   private String bundleVersion;
   private String bundleBuildTimestamp;

   public String getBundleBuildTimestampDigitsOnly() {
      return this.bundleBuildTimestamp.replaceAll("\\D", "");
   }

   public String getBundleName() {
      return this.bundleName;
   }

   public String getBundleVendor() {
      return this.bundleVendor;
   }

   public String getAutomaticModuleName() {
      return this.automaticModuleName;
   }

   public String getBundleVersion() {
      return this.bundleVersion;
   }

   public String getBundleBuildTimestamp() {
      return this.bundleBuildTimestamp;
   }

   public void setBundleName(String bundleName) {
      this.bundleName = bundleName;
   }

   public void setBundleVendor(String bundleVendor) {
      this.bundleVendor = bundleVendor;
   }

   public void setAutomaticModuleName(String automaticModuleName) {
      this.automaticModuleName = automaticModuleName;
   }

   public void setBundleVersion(String bundleVersion) {
      this.bundleVersion = bundleVersion;
   }

   public void setBundleBuildTimestamp(String bundleBuildTimestamp) {
      this.bundleBuildTimestamp = bundleBuildTimestamp;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Version)) {
         return false;
      } else {
         Version other = (Version)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            label71: {
               Object this$bundleName = this.getBundleName();
               Object other$bundleName = other.getBundleName();
               if (this$bundleName == null) {
                  if (other$bundleName == null) {
                     break label71;
                  }
               } else if (this$bundleName.equals(other$bundleName)) {
                  break label71;
               }

               return false;
            }

            Object this$bundleVendor = this.getBundleVendor();
            Object other$bundleVendor = other.getBundleVendor();
            if (this$bundleVendor == null) {
               if (other$bundleVendor != null) {
                  return false;
               }
            } else if (!this$bundleVendor.equals(other$bundleVendor)) {
               return false;
            }

            label57: {
               Object this$automaticModuleName = this.getAutomaticModuleName();
               Object other$automaticModuleName = other.getAutomaticModuleName();
               if (this$automaticModuleName == null) {
                  if (other$automaticModuleName == null) {
                     break label57;
                  }
               } else if (this$automaticModuleName.equals(other$automaticModuleName)) {
                  break label57;
               }

               return false;
            }

            Object this$bundleVersion = this.getBundleVersion();
            Object other$bundleVersion = other.getBundleVersion();
            if (this$bundleVersion == null) {
               if (other$bundleVersion != null) {
                  return false;
               }
            } else if (!this$bundleVersion.equals(other$bundleVersion)) {
               return false;
            }

            Object this$bundleBuildTimestamp = this.getBundleBuildTimestamp();
            Object other$bundleBuildTimestamp = other.getBundleBuildTimestamp();
            if (this$bundleBuildTimestamp == null) {
               if (other$bundleBuildTimestamp == null) {
                  return true;
               }
            } else if (this$bundleBuildTimestamp.equals(other$bundleBuildTimestamp)) {
               return true;
            }

            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof Version;
   }

   public int hashCode() {
      int PRIME = true;
      int result = 1;
      Object $bundleName = this.getBundleName();
      int result = result * 59 + ($bundleName == null ? 43 : $bundleName.hashCode());
      Object $bundleVendor = this.getBundleVendor();
      result = result * 59 + ($bundleVendor == null ? 43 : $bundleVendor.hashCode());
      Object $automaticModuleName = this.getAutomaticModuleName();
      result = result * 59 + ($automaticModuleName == null ? 43 : $automaticModuleName.hashCode());
      Object $bundleVersion = this.getBundleVersion();
      result = result * 59 + ($bundleVersion == null ? 43 : $bundleVersion.hashCode());
      Object $bundleBuildTimestamp = this.getBundleBuildTimestamp();
      result = result * 59 + ($bundleBuildTimestamp == null ? 43 : $bundleBuildTimestamp.hashCode());
      return result;
   }

   public String toString() {
      String var10000 = this.getBundleName();
      return "Version(bundleName=" + var10000 + ", bundleVendor=" + this.getBundleVendor() + ", automaticModuleName=" + this.getAutomaticModuleName() + ", bundleVersion=" + this.getBundleVersion() + ", bundleBuildTimestamp=" + this.getBundleBuildTimestamp() + ")";
   }

   public Version() {
   }

   public Version(String bundleName, String bundleVendor, String automaticModuleName, String bundleVersion, String bundleBuildTimestamp) {
      this.bundleName = bundleName;
      this.bundleVendor = bundleVendor;
      this.automaticModuleName = automaticModuleName;
      this.bundleVersion = bundleVersion;
      this.bundleBuildTimestamp = bundleBuildTimestamp;
   }
}
