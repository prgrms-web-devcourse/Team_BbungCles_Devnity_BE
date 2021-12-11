package com.devnity.devnity.domain.gather.entity.category;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatherCommentStatus {
  POSTED,
  DELETED;
}
