package com.devnity.devnity.web.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.web.mapgakco.dto.SimpleMapgakcoInfoDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MapgakcoPageResponse {

  private Double lastDistance;
  private Boolean hasNext;
  private List<SimpleMapgakcoInfoDto> mapgakcos;

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
