package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller;

import com.polarion.core.config.Configuration;
import io.swagger.v3.jaxrs2.integration.resources.BaseOpenApiResource;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.jetbrains.annotations.NotNull;

@Hidden
@Path("/swagger/definition.json")
public class SwaggerDefinitionController extends BaseOpenApiResource {
   public static final String BEARER_AUTH = "bearerAuth";
   @Context
   private ServletConfig config;
   @Context
   private Application application;

   @GET
   @Produces({"application/json"})
   @Operation(
      hidden = true
   )
   @NotNull
   public Response openApi(@Context @NotNull HttpHeaders headers, @Context @NotNull UriInfo uriInfo) throws Exception {
      SwaggerConfiguration swaggerConfig = (new SwaggerConfiguration()).openAPI(this.createOpenApi(uriInfo)).prettyPrint(true).resourcePackages(Set.of("io.swagger.sample.resource"));
      this.setOpenApiConfiguration(swaggerConfig);
      return super.getOpenApi(headers, this.config, this.application, uriInfo, "json");
   }

   @NotNull
   private OpenAPI createOpenApi(@NotNull UriInfo uriInfo) {
      OpenAPI openApiSettings = new OpenAPI();
      openApiSettings.info(this.createInfo());
      openApiSettings.setServers(this.createServers(uriInfo));
      openApiSettings.addSecurityItem((new SecurityRequirement()).addList("bearerAuth"));
      openApiSettings.components(this.createComponents());
      return openApiSettings;
   }

   @NotNull
   private Info createInfo() {
      return (new Info()).title("REST API");
   }

   @NotNull
   private List<Server> createServers(@NotNull UriInfo uriInfo) {
      Server defaultServer = new Server();
      String url = UriBuilder.fromUri(Configuration.getInstance().rest().baseURL().toString()).path(uriInfo.getBaseUri().getPath()).build(new Object[0]).toString();
      defaultServer.setUrl(url);
      return List.of(defaultServer);
   }

   @NotNull
   private Components createComponents() {
      Components components = new Components();
      components.addSecuritySchemes("bearerAuth", this.getBearerScheme());
      return components;
   }

   @NotNull
   private SecurityScheme getBearerScheme() {
      return (new SecurityScheme()).name("bearerAuth").type(Type.HTTP).scheme("bearer").bearerFormat("JWT");
   }
}
