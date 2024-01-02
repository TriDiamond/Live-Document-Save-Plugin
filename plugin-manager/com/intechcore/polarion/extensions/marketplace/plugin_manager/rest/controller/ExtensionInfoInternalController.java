package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Context;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model.Version;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.bundle.BundleInformation;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.bundle.BundleService;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.extension.ExtensionService;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.extension.InstalledExtension;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo.SystemInfoService;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.service.systeminfo.SystemInformation;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.util.ExtensionInfo;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Hidden
@Path("/internal")
@Tag(
   name = "Extension Information"
)
@OpenAPIDefinition(
   info = @Info(
   title = "REST API"
)
)
public class ExtensionInfoInternalController {
   @GET
   @Produces({"application/json"})
   @Path("/context")
   @Operation(
      summary = "Returns basic context information of Polarion's extension"
   )
   public Context getContext() {
      return ExtensionInfo.getInstance().getContext();
   }

   @GET
   @Produces({"application/json"})
   @Path("/version")
   @Operation(
      summary = "Returns version of Polarion's extension"
   )
   public Version getVersion() {
      return ExtensionInfo.getInstance().getVersion();
   }

   @GET
   @Produces({"application/json"})
   @Path("/extensions")
   @Operation(
      summary = "Returns list of installed extensions"
   )
   public List<InstalledExtension> getExtensions() {
      return ExtensionService.getInstance().getInstalledExtensions();
   }

   @GET
   @Produces({"application/json"})
   @Path("/systeminfo")
   @Operation(
      summary = "Returns system information"
   )
   public SystemInformation getSystemInformation() {
      return SystemInfoService.getInstance().getSystemInformation();
   }

   @GET
   @Produces({"application/json"})
   @Path("/bundles")
   @Operation(
      summary = "Returns List of Bundles"
   )
   public List<BundleInformation> getBundles() {
      return BundleService.getInstance().getBundles();
   }

   @GET
   @Produces({"application/json"})
   @Path("/bundles/{bundleId}")
   @Operation(
      summary = "Returns Bundle by Bundle ID"
   )
   public BundleInformation getBundle(@PathParam("bundleId") long bundleId) {
      return BundleService.getInstance().getBundle(bundleId);
   }
}
