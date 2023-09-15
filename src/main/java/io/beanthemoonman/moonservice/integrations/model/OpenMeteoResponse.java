package io.beanthemoonman.moonservice.integrations.model;

import java.math.BigDecimal;

public record OpenMeteoResponse(
  Double latitude,
  Double longitude,
  BigDecimal generationtime_ms,
  Integer utc_offset_seconds,
  String timezone,
  String timezone_abbreviation,
  Integer elevation,
  CurrentWeather current_weather
) {
}
