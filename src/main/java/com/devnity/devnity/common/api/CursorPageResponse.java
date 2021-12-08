package com.devnity.devnity.common.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CursorPageResponse<T> {

  private List<T> values;
  private Long nextLastId; // 다음 페이지의 lastId, 다음 페이지가 없으면 null
  private Boolean isFirstPage; // 현재 페이지가 첫 페이지 인지 여부
  private Boolean isLastPage; // 현재 페이지가 마지막 페이지 인지 여부
  private Integer numberOfElement; // 현재 페이지에 나올 데이터 수
  private Integer totalPages; // 전체 페이지 수
}
