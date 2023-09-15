package io.beanthemoonman.moonservice.integrations;

import com.google.gson.Gson;
import io.beanthemoonman.moonservice.integrations.model.IpApiResponse;

import java.io.IOException;

public class IpApi extends Integration {

  private static final String API_URL = "http://ip-api.com/";

  public static IpApiResponse getIpGeoData(String ipAddress) throws IOException {
    return new Gson().fromJson(makeSimpleGetRequest(API_URL + "json/" + ipAddress), IpApiResponse.class);
  }
}
