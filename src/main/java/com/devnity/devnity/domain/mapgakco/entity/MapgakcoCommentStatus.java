package com.devnity.devnity.domain.mapgakco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MapgakcoCommentStatus {
  POSTED, DELETED;
}
