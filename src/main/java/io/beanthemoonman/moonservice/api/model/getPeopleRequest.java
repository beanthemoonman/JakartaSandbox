package io.beanthemoonman.moonservice.api.model;

public record getPeopleRequest(
    String query,
    String column,
    Integer start,
    Integer count
) {
}
