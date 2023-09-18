package io.beanthemoonman.moonservice.api;

import io.beanthemoonman.moonservice.integrations.IfConfig;
import io.beanthemoonman.moonservice.integrations.IpApi;
import io.beanthemoonman.moonservice.integrations.OpenMeteo;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static java.lang.StringTemplate.STR;

@Path("/moon")
public class MoonResource extends AbstractResource {

  @GET
  @Path("/time")
  @Produces(MediaType.TEXT_PLAIN)
  public Response time(
      @QueryParam("zone") String zone
  ) {
    return Response.ok(
        getTime(!ZoneId.getAvailableZoneIds().contains(zone) ? "UTC" : zone)
    ).build();
  }

  @GET
  @Path("/weather")
  @Produces(MediaType.TEXT_PLAIN)
  public Response weather(){
    try {
      var ipAddress = getClientIpAddress(request);
      if ("127.0.0.1".equals(ipAddress)) {
        ipAddress = IfConfig.getServerIp();
      }
      var ipData = IpApi.getIpGeoData(ipAddress);
      var weatherData = OpenMeteo.getWeatherData(ipData.lat(), ipData.lon());
      return Response.ok(
          STR."It is currently \{weatherData.current_weather().temperature()}° Celsius in, \{ipData.city()}" +
          STR.", \{ipData.regionName()}. The time is \{getTime(ipData.timezone())}."
      ).build();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String getTime(String timezone) {
    return Instant.now().atZone(ZoneId.of(timezone)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
}
