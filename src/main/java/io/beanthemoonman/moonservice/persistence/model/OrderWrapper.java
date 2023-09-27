package io.beanthemoonman.moonservice.persistence.model;

public record OrderWrapper(
    String column,
    OrderSort orderSort
) {
}
