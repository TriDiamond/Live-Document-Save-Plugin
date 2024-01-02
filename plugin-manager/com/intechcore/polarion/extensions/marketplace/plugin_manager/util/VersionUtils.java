package com.intechcore.polarion.extensions.marketplace.plugin_manager.util;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Version;
import java.util.jar.Attributes;
import org.jetbrains.annotations.NotNull;

public final class VersionUtils {
   public static final String BUNDLE_NAME = "Bundle-Name";
   public static final String BUNDLE_VENDOR = "Bundle-Vendor";
   public static final String AUTOMATIC_MODULE_NAME = "Automatic-Module-Name";
   public static final String BUNDLE_VERSION = "Bundle-Version";
   public static final String BUNDLE_BUILD_TIMESTAMP = "Bundle-Build-Timestamp";

   @NotNull
   public static Version getVersion() {
      Attributes attributes = ManifestUtils.getManifestAttributes();
      String bundleName = attributes.getValue("Bundle-Name");
      String bundleVendor = attributes.getValue("Bundle-Vendor");
      String automaticModuleName = attributes.getValue("Automatic-Module-Name");
      String bundleVersion = attributes.getValue("Bundle-Version");
      String bundleBuildTimestamp = attributes.getValue("Bundle-Build-Timestamp");
      return new Version(bundleName, bundleVendor, automaticModuleName, bundleVersion, bundleBuildTimestamp);
   }

   private VersionUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
