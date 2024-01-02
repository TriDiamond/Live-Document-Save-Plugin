package com.intechcore.polarion.extensions.marketplace.plugin_manager.util;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class PolarionUtils {
   @NotNull
   public static String getPolarionHomePath() {
      return (String)Objects.requireNonNull(System.getProperty("com.polarion.home", (String)null));
   }

   @NotNull
   public static String getExternalPropertyPath() {
      return (String)Objects.requireNonNull(System.getProperty("com.polarion.propertyFile", (String)null));
   }

   private PolarionUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
