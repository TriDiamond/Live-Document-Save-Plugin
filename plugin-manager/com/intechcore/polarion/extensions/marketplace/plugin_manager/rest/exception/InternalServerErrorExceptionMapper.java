package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<InternalServerErrorException> {
   public Response toResponse(InternalServerErrorException e) {
      return Response.status(Status.INTERNAL_SERVER_ERROR.getStatusCode()).entity(e.getMessage()).build();
   }
}
