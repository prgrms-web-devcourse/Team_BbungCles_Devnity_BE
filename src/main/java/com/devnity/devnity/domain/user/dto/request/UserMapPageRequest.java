package com.devnity.devnity.domain.user.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class UserMapPageRequest {

  private Double lastDistance;

  private Double centerX;
  private Double centerY;

  private Double currentNEX;
  private Double currentNEY;
  private Double currentSWX;
  private Double currentSWY;
}
