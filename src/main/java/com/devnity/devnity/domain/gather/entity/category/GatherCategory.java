package com.devnity.devnity.domain.gather.entity.category;

import com.fasterxml.jackson.annotation.JsonFormat;

// json -> 객체 (역직렬화)
//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatherCategory {
  STUDY,
  CLUB,
  PROJECT;
}
