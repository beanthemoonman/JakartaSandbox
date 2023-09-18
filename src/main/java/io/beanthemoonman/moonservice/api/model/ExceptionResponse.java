package io.beanthemoonman.moonservice.api.model;

public record ExceptionResponse(
    String name,
    String cause,
    String stackTrace
) {
}
