package io.beanthemoonman.moonservice.api.model;

public record getPeopleRequest(
    Object query,
    String column,
    Integer start,
    Integer count
) {
}
