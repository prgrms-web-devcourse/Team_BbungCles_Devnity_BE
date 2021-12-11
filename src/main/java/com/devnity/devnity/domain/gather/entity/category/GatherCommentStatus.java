package com.devnity.devnity.domain.gather.entity.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatherCommentStatus {
  POSTED("POSTED"),
  DELETED("DELETED");

  private final String status;

  @JsonCreator
  GatherCommentStatus(@JsonProperty("status") String status) {
    this.status = status;
  }
}
