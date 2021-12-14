package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoCommentStatus {
  POSTED("POSTED"),
  DELETED("DELETED");

  private final String status;

  @JsonCreator
  MapgakcoCommentStatus(@JsonProperty("status") String status) {
    this.status = status;
  }

}
