package io.beanthemoonman.moonservice.api.model;

public record DataModificationResponse(
    String message,
    Boolean error
) {

  public static DataModificationResponse createSuccess() {
    return new DataModificationResponse(
        "Success", Boolean.FALSE
    );
  }

  public static DataModificationResponse createFailure(String message) {
    return new DataModificationResponse(
        message, Boolean.TRUE
    );
  }
}
