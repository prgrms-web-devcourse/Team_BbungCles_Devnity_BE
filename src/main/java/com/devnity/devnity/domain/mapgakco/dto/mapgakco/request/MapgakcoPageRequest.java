package com.devnity.devnity.domain.mapgakco.dto.mapgakco.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoPageRequest {

  private Double lastNEX;
  private Double lastNEY;
  private Double lastSWX;
  private Double lastSWY;

  private Double currentNEX;
  private Double currentNEY;
  private Double currentSWX;
  private Double currentSWY;
}
