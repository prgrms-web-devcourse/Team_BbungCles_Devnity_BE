package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoStatus {
  GATHERING("모집중"),
  CLOSED("모집마감"),
  FULL("인원초과"),
  DELETED("삭제됨");

  @JsonValue
  private final String status;

  MapgakcoStatus(final String status) {
    this.status = status;
  }

}
