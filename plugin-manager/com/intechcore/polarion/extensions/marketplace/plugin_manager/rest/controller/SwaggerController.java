package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller;

import com.polarion.core.config.Configuration;
import com.polarion.core.config.IRestConfiguration;
import io.swagger.v3.oas.annotations.Hidden;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import org.jetbrains.annotations.NotNull;

@Hidden
@Path("/swagger")
public class SwaggerController {
   private static final String URI_PATTERN = "$uri_replace";
   private static final String PERSISTENCE_PATTERN = "$persistence_replace";
   @Context
   private UriInfo uriInfo;

   @GET
   @Produces({"text/html"})
   @NotNull
   public Response swagger() throws IOException {
      IRestConfiguration configuration = Configuration.getInstance().rest();
      if (configuration.enabled() && configuration.swaggerUiEnabled()) {
         InputStream inputStream = this.getClass().getResourceAsStream("/swagger/swagger_ui.html");

         Response var5;
         try {
            URI uri = this.uriInfo.getAbsolutePath().resolve("swagger/definition.json");
            String html = (new String(((InputStream)Objects.requireNonNull(inputStream)).readAllBytes(), StandardCharsets.UTF_8)).replace("$uri_replace", uri.toString()).replace("$persistence_replace", String.valueOf(configuration.swaggerUiPersistAuthorization()));
            var5 = Response.status(Status.OK).entity(html).build();
         } catch (Throwable var7) {
            if (inputStream != null) {
               try {
                  inputStream.close();
               } catch (Throwable var6) {
                  var7.addSuppressed(var6);
               }
            }

            throw var7;
         }

         if (inputStream != null) {
            inputStream.close();
         }

         return var5;
      } else {
         return Response.status(Status.SERVICE_UNAVAILABLE).build();
      }
   }
}
