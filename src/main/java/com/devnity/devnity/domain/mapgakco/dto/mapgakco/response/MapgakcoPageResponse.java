package com.devnity.devnity.domain.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MapgakcoPageResponse {

  private List<SimpleMapgakcoInfoDto> mapgakcos;
  private Double lastDistance;
  private Boolean hasNext;

  @Builder
  public MapgakcoPageResponse(List<SimpleMapgakcoInfoDto> mapgakcos, Double lastDistance, Boolean hasNext) {
    this.mapgakcos = mapgakcos;
    this.lastDistance = lastDistance;
    this.hasNext = hasNext;
  }

  public static MapgakcoPageResponse of(List<SimpleMapgakcoInfoDto> mapgakcos, Double lastDistance, Boolean hasNext) {
    return MapgakcoPageResponse.builder()
      .mapgakcos(mapgakcos)
      .lastDistance(lastDistance)
      .hasNext(hasNext)
      .build();
  }

}
