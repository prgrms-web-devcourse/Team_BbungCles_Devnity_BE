package com.devnity.devnity.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageRequest {

  private Long lastId;
  private Integer size;

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CursorPageRequest{");
    sb.append("lastId=").append(lastId);
    sb.append(", size=").append(size);
    sb.append('}');
    return sb.toString();
  }
}
