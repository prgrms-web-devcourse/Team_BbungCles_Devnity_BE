package com.devnity.devnity.web.common.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageResponse<T> {

  private List<T> values;
  private Long nextLastId;
}
