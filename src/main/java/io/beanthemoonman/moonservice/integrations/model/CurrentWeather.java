package io.beanthemoonman.moonservice.integrations.model;

public record CurrentWeather(
  Double temperature,
  Double windspeed,
  Integer winddirection,
  Short weathercode,
  Short is_day,
  String time
) {
}
