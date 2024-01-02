package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.filter.Secured;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Context;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Version;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.bundle.BundleInformation;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.extension.InstalledExtension;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo.SystemInformation;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.util.ApiControllerUtils;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import javax.ws.rs.Path;

@Secured
@Path("/api")
public class ExtensionInfoApiController extends ExtensionInfoInternalController {
   @Hidden
   public Context getContext() {
      return (Context)ApiControllerUtils.callPrivileged(() -> {
         return super.getContext();
      });
   }

   public Version getVersion() {
      return (Version)ApiControllerUtils.callPrivileged(() -> {
         return super.getVersion();
      });
   }

   public List<InstalledExtension> getExtensions() {
      return (List)ApiControllerUtils.callPrivileged(() -> {
         return super.getExtensions();
      });
   }

   public SystemInformation getSystemInformation() {
      return (SystemInformation)ApiControllerUtils.callPrivileged(() -> {
         return super.getSystemInformation();
      });
   }

   public List<BundleInformation> getBundles() {
      return (List)ApiControllerUtils.callPrivileged(() -> {
         return super.getBundles();
      });
   }

   public BundleInformation getBundle(long bundleId) {
      return super.getBundle(bundleId);
   }
}
