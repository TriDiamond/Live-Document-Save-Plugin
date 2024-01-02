package com.intechcore.polarion.extensions.marketplace.plugin_manager;

import com.polarion.core.util.logging.Logger;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class ManagerPlugin extends Plugin {
   private static final Logger logger = Logger.getLogger(ManagerPlugin.class);
   private static ManagerPlugin instance;
   private BundleContext bundleContext;

   public void start(BundleContext context) throws Exception {
      logger.info("Start");
      super.start(context);
      instance = this;
      this.bundleContext = context;
   }

   public static ManagerPlugin getInstance() {
      return instance;
   }

   public BundleContext getBundleContext() {
      return this.bundleContext;
   }
}
