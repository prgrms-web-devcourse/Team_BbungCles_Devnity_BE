package com.devnity.devnity.domain.user.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserMapPageResponse<T> {

  private Double lastDistance;
  private Boolean hasNext;
  private List<T> values;

}
