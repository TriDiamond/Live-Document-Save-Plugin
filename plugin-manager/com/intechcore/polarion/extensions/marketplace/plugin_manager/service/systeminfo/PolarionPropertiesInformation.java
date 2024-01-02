package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.util.PolarionUtils;
import com.polarion.core.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PolarionPropertiesInformation {
   private static final Logger logger = Logger.getLogger(PolarionPropertiesInformation.class);
   private final Properties properties;

   public PolarionPropertiesInformation() {
      Properties properties = null;

      try {
         properties = new Properties();
         File polarionPropertiesFile = new File(PolarionUtils.getExternalPropertyPath());
         FileInputStream fis = new FileInputStream(polarionPropertiesFile);

         try {
            properties.load(fis);
         } catch (Throwable var7) {
            try {
               fis.close();
            } catch (Throwable var6) {
               var7.addSuppressed(var6);
            }

            throw var7;
         }

         fis.close();
         List var9 = (List)properties.keySet().stream().filter((o) -> {
            return o.toString().toLowerCase().endsWith("password") || o.toString().toLowerCase().endsWith("passwd") || o.toString().toLowerCase().endsWith("internalpg");
         }).map(Object::toString).collect(Collectors.toList());
         Stream var10000 = var9.stream();
         Objects.requireNonNull(properties);
         var10000.forEach(properties::remove);
      } catch (IOException var8) {
         logger.error("Error Reading Polarion Properties", var8);
      }

      this.properties = properties;
   }

   public Properties getProperties() {
      return this.properties;
   }
}
