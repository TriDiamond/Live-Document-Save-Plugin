package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
   public Response toResponse(NotFoundException e) {
      return Response.status(Status.NOT_FOUND.getStatusCode()).entity(e.getMessage()).build();
   }
}
