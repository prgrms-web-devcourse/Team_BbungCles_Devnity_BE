package com.devnity.devnity.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageRequest {

  private Long lastId;
  private Integer size;

}
