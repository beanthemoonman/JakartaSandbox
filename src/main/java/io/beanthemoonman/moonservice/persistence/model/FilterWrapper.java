package io.beanthemoonman.moonservice.persistence.model;

public record FilterWrapper(
    Object query,
    String column,
    FilterType filterType
) {
}
