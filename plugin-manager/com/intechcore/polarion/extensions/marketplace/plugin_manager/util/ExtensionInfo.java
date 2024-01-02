package com.intechcore.polarion.extensions.marketplace.plugin_manager.util;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Context;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Version;

public final class ExtensionInfo {
   private final Version version = VersionUtils.getVersion();
   private final Context context = ContextUtils.getContext();

   private ExtensionInfo() {
   }

   public static ExtensionInfo getInstance() {
      return ExtensionInfo.ExtensionInfoHolder.INSTANCE;
   }

   public Version getVersion() {
      return this.version;
   }

   public Context getContext() {
      return this.context;
   }

   private static class ExtensionInfoHolder {
      private static final ExtensionInfo INSTANCE = new ExtensionInfo();
   }
}
