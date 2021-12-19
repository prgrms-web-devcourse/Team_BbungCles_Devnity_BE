package com.devnity.devnity.domain.mapgakco.dto.mapgakco.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MapgakcoRequest {

  @NotNull
  private Double currentNEX;
  @NotNull
  private Double currentNEY;
  @NotNull
  private Double currentSWX;
  @NotNull
  private Double currentSWY;

  @Builder
  public MapgakcoRequest(Double currentNEX, Double currentNEY, Double currentSWX, Double currentSWY) {
    this.currentNEX = currentNEX;
    this.currentNEY = currentNEY;
    this.currentSWX = currentSWX;
    this.currentSWY = currentSWY;
  }
}
