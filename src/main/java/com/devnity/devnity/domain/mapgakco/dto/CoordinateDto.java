package com.devnity.devnity.domain.mapgakco.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CoordinateDto {

  private Double northEastX;
  private Double northEastY;
  private Double southWestX;
  private Double southWestY;

  @Builder
  public CoordinateDto(Double northEastX, Double northEastY, Double southWestX, Double southWestY) {
    this.northEastX = northEastX;
    this.northEastY = northEastY;
    this.southWestX = southWestX;
    this.southWestY = southWestY;
  }

  public static CoordinateDto of(Double northEastX, Double northEastY, Double southWestX, Double southWestY) {
    return CoordinateDto.builder()
      .northEastX(northEastX)
      .northEastY(northEastY)
      .southWestX(southWestX)
      .southWestY(southWestY)
      .build();
  }

}
