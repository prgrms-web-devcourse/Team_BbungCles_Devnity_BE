package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoStatus {
  GATHERING("GATHERING"),
  CLOSED("CLOSED"),
  FULL("FULL"),
  DELETED("DELETED");

  private final String status;

  @JsonCreator
  MapgakcoStatus(@JsonProperty("status") String status) {
    this.status = status;
  }

}
