package io.beanthemoonman.moonservice.api;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/v2")
public class MoonV2Resource extends MoonV1Resource{

  @Override
  public Response time(
      String zone
  ) {
    return Response.ok(STR."Check your own watch set for timezone: \{zone}!").build();
  }
}
