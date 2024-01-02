package com.intechcore.polarion.extensions.marketplace.plugin_manager;

import com.polarion.core.util.logging.Logger;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

public class AdminUiServlet extends HttpServlet {
   private static final Logger logger = Logger.getLogger(AdminUiServlet.class);
   private static final long serialVersionUID = 4323903250755251706L;
   private static final String PLUGIN_MANAGER_ADMIN = "plugin-manager-admin";

   private static void setContentType(@NotNull String uri, @NotNull HttpServletResponse response) {
	   System.out.println("Admin Servlet Page setContentType Method its Working");
      if (uri.endsWith(".js")) {
         response.setContentType("text/javascript");
      } else if (uri.endsWith(".html")) {
         response.setContentType("text/html");
      } else if (uri.endsWith(".png")) {
         response.setContentType("image/png");
      } else if (uri.endsWith(".css")) {
         response.setContentType("text/css");
      }

   }

   protected void service(HttpServletRequest request, HttpServletResponse response) {
	  System.out.println("Admin Servlet Page service Method its Working");
      String uri = request.getRequestURI();
      String relativeUri = uri.substring("/polarion/".length());
      if (relativeUri.startsWith("plugin-manager-admin/ui/")) {
         this.serveResource(response, relativeUri.substring("plugin-manager-admin/ui".length()));
      }

   }

   private void serveResource(@NotNull HttpServletResponse response, @NotNull String uri) {
      try {
    	 System.out.println("Admin Servlet Page SaveResource Method its Working");
         InputStream inputStream = this.getServletContext().getResourceAsStream(uri);

         try {
            if (inputStream == null) {
               response.sendError(404);
            } else {
               ServletOutputStream outputStream = response.getOutputStream();

               try {
                  setContentType(uri, response);
                  IOUtils.copy(inputStream, outputStream);
               } catch (Throwable var9) {
                  if (outputStream != null) {
                     try {
                        outputStream.close();
                     } catch (Throwable var8) {
                        var9.addSuppressed(var8);
                     }
                  }

                  throw var9;
               }

               if (outputStream != null) {
                  outputStream.close();
               }
            }
         } catch (Throwable var10) {
            if (inputStream != null) {
               try {
                  inputStream.close();
               } catch (Throwable var7) {
                  var10.addSuppressed(var7);
               }
            }

            throw var10;
         }

         if (inputStream != null) {
            inputStream.close();
         }
      } catch (IOException var11) {
         logger.error(var11.getMessage(), var11);
      }

   }
}
