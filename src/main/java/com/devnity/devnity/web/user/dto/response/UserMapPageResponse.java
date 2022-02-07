package com.devnity.devnity.web.user.dto.response;

import com.devnity.devnity.web.user.dto.SimpleUserMapInfoDto;
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
public class UserMapPageResponse {

  private Double lastDistance;
  private Boolean hasNext;
  private List<SimpleUserMapInfoDto> users;

}
