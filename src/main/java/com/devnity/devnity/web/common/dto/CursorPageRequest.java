package com.devnity.devnity.web.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CursorPageRequest {

  private static final int DEFAULT_SIZE = 5;
  private static final int MAX_SIZE = 20;

  private Long lastId;
  private Integer size;

  public CursorPageRequest(Long lastId, Integer size) {
    this.lastId = lastId;
    this.size = size == null ? DEFAULT_SIZE : (size > MAX_SIZE ? DEFAULT_SIZE : size);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CursorPageRequest{");
    sb.append("lastId=").append(lastId);
    sb.append(", size=").append(size);
    sb.append('}');
    return sb.toString();
  }
}
