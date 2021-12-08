package com.devnity.devnity.common.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageResponse<T> {

  private List<T> values;
  private Long nextLastId;
  private Boolean hasNext;

}
