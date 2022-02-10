package com.devnity.devnity.web.mapgakco.dto.mapgakco.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MapgakcoPageRequest {

  @NotNull
  private Double lastDistance;
  @NotNull
  private Double centerX;
  @NotNull
  private Double centerY;
  @NotNull
  private Double currentNEX;
  @NotNull
  private Double currentNEY;
  @NotNull
  private Double currentSWX;
  @NotNull
  private Double currentSWY;

  @Builder
  public MapgakcoPageRequest(Double lastDistance, Double centerX, Double centerY,
    Double currentNEX, Double currentNEY, Double currentSWX, Double currentSWY) {
    this.lastDistance = lastDistance;
    this.centerX = centerX;
    this.centerY = centerY;
    this.currentNEX = currentNEX;
    this.currentNEY = currentNEY;
    this.currentSWX = currentSWX;
    this.currentSWY = currentSWY;
  }

}
