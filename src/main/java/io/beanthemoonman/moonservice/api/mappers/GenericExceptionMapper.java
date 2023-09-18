package io.beanthemoonman.moonservice.api.mappers;

import io.beanthemoonman.moonservice.api.model.ExceptionResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  private String getStackTrace(StackTraceElement[] trace) {
    var ret = new StringBuilder();
    for (var line : trace) {
      ret.append(line.getClassName()).append(" Line: ").append(line.getLineNumber()).append("\n");
    }
    return ret.toString();
  }

  @Override
  @Produces(MediaType.APPLICATION_JSON)
  public Response toResponse(Exception exception) {
    return Response.serverError().entity(
        new ExceptionResponse(
            exception.getClass().getName(),
            exception.getMessage().isEmpty() ? "No message" : exception.getMessage(),
            getStackTrace(exception.getCause().getStackTrace())
        )
    ).build();
  }
}
