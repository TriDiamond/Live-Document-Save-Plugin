package com.intechcore.polarion.extensions.marketplace.plugin_manager.util;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Context;
import java.util.jar.Attributes;
import org.jetbrains.annotations.NotNull;

public final class ContextUtils {
   public static final String EXTENSION_CONTEXT = "Extension-Context";

   @NotNull
   public static Context getContext() {
      Attributes attributes = ManifestUtils.getManifestAttributes();
      String extensionContext = attributes.getValue("Extension-Context");
      return new Context(extensionContext);
   }

   private ContextUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
