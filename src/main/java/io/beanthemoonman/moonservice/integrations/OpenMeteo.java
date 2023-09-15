package io.beanthemoonman.moonservice.integrations;

import com.google.gson.Gson;
import io.beanthemoonman.moonservice.integrations.model.OpenMeteoResponse;

import java.io.IOException;

public class OpenMeteo extends Integration {

  private static final String API_URL = "https://api.open-meteo.com/";

  public static OpenMeteoResponse getWeatherData(Double lat, Double lon) throws IOException {
    return new Gson().fromJson(makeSimpleGetRequest(
        API_URL +
            "/v1/forecast?latitude=" +
            lat.toString() +
            "&longitude=" +
            lon.toString() +
            "&current_weather=true"
    ), OpenMeteoResponse.class);
  }
}
