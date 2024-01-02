package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception;

import com.polarion.core.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UncaughtExceptionMapper implements ExceptionMapper<Throwable> {
   private static final Logger logger = Logger.getLogger(UncaughtExceptionMapper.class);

   public Response toResponse(Throwable throwable) {
      if (throwable instanceof WebApplicationException) {
         WebApplicationException webapplicationexception = (WebApplicationException)throwable;
         return webapplicationexception.getResponse();
      } else {
         logger.error("Uncaught exception", throwable);
         return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(throwable.getMessage()).build();
      }
   }
}
