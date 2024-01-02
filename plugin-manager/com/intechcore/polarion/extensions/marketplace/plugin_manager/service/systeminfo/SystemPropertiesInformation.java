package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo;

import java.util.Properties;

public class SystemPropertiesInformation {
   private final Properties properties = System.getProperties();

   public Properties getProperties() {
      return this.properties;
   }
}
