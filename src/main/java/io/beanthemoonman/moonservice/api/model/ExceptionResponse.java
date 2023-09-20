package io.beanthemoonman.moonservice.api.model;

import java.util.List;

public record ExceptionResponse(
    String name,
    String cause,
    List<String> stackTrace
) {
}
