package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
   public Response toResponse(IllegalArgumentException e) {
      return Response.status(Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
   }
}
