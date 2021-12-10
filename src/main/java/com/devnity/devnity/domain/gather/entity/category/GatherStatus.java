package com.devnity.devnity.domain.gather.entity.category;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatherStatus {
  GATHERING, CLOSED, FULL, DELETED;
}
