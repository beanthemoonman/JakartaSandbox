package io.beanthemoonman.moonservice.api.mappers;

import io.beanthemoonman.moonservice.api.model.ExceptionResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.ArrayList;
import java.util.List;

public class GenericExceptionMapper implements ExceptionMapper<Exception> {

  private List<String> getStackTrace(StackTraceElement[] trace) {
    var ret = new ArrayList<String>();
    for (var line : trace) {
      ret.add(
          STR."\{line.getClassName()}, (\{line.getMethodName()}) Line:\{line.getLineNumber()}"
      );
    }
    return ret;
  }

  @Override
  @Produces(MediaType.APPLICATION_JSON)
  public Response toResponse(Exception exception) {
    return Response.serverError().entity(
        new ExceptionResponse(
            exception.getClass().getName(),
            exception.getMessage().isEmpty() ? "No message" : exception.getMessage(),
            exception.getCause() != null ? getStackTrace(exception.getCause().getStackTrace()) : List.of("")
        )
    ).build();
  }
}
