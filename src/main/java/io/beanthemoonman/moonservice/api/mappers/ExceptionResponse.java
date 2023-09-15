package io.beanthemoonman.moonservice.api.mappers;

public record ExceptionResponse(
    String name,
    String cause,
    String stackTrace
) {
}
