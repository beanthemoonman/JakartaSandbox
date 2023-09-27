package io.beanthemoonman.moonservice.api.model;

import io.beanthemoonman.moonservice.persistence.model.FilterWrapper;
import io.beanthemoonman.moonservice.persistence.model.OrderWrapper;

import java.util.List;

public record GenericLookupRequest(
    String classname,
    List<FilterWrapper> filters,
    List<OrderWrapper> orders,
    Integer start,
    Integer count
) {
}
