package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoStatus {
  GATHERING("GATHERING"),
  CLOSED("CLOSED"),
  FULL("FULL"),
  DELETED("DELETED");

  @JsonValue
  private final String status;

  MapgakcoStatus(@JsonProperty("status") String status) {
    this.status = status;
  }

}
