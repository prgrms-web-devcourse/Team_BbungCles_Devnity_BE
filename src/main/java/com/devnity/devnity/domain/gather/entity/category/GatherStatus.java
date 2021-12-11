package com.devnity.devnity.domain.gather.entity.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatherStatus {
  GATHERING("GATHERING"),
  CLOSED("CLOSED"),
  FULL("FULL"),
  DELETED("DELETED");

  private final String status;

  @JsonCreator
  GatherStatus(@JsonProperty("status") String status){
    this.status = status;
  }
}
