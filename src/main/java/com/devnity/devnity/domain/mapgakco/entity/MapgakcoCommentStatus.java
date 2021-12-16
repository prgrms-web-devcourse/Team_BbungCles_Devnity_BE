package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoCommentStatus {
  POSTED("POSTED"),
  DELETED("DELETED");

  @JsonValue
  private final String status;

  MapgakcoCommentStatus(String status) {
    this.status = status;
  }

}
