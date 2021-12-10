package com.devnity.devnity.domain.gather.entity.category;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GatherCategory {
  STUDY("STUDY"),
  CLUB("CLUB"),
  PROJECT("PROJECT");

  private final String status;

  @JsonCreator
  GatherCategory(@JsonProperty("status") String status){
    this.status = status;
  }
}
