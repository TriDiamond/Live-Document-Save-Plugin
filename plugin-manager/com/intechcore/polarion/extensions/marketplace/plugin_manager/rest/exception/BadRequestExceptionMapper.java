package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
   public Response toResponse(BadRequestException e) {
      return Response.status(Status.BAD_REQUEST.getStatusCode()).entity(e.getMessage()).build();
   }
}
