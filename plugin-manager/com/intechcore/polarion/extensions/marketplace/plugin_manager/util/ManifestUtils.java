package com.intechcore.polarion.extensions.marketplace.plugin_manager.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.ws.rs.InternalServerErrorException;

public final class ManifestUtils {
   public static Attributes getManifestAttributes() {
      try {
         Enumeration<URL> resources = VersionUtils.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
         if (resources.hasMoreElements()) {
            InputStream inputStream = ((URL)resources.nextElement()).openStream();

            Attributes var3;
            try {
               Manifest manifest = new Manifest(inputStream);
               var3 = manifest.getMainAttributes();
            } catch (Throwable var5) {
               if (inputStream != null) {
                  try {
                     inputStream.close();
                  } catch (Throwable var4) {
                     var5.addSuppressed(var4);
                  }
               }

               throw var5;
            }

            if (inputStream != null) {
               inputStream.close();
            }

            return var3;
         } else {
            throw new InternalServerErrorException("Manifest information could not be found or read");
         }
      } catch (Throwable var6) {
         throw var6;
      }
   }

   private ManifestUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
