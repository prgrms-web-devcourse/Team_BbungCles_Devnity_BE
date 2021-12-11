package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoCommentStatus {
  POSTED("게시됨"),
  DELETED("삭제됨");

  @JsonValue
  private final String status;

  MapgakcoCommentStatus(final String status) {
    this.status = status;
  }

}
