package com.devnity.devnity.domain.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MapgakcoStatusResponse {

  private MapgakcoStatus status;

  @Builder
  public MapgakcoStatusResponse(MapgakcoStatus status) {
    this.status = status;
  }

  public static MapgakcoStatusResponse of(MapgakcoStatus status) {
    return MapgakcoStatusResponse.builder()
      .status(status)
      .build();
  }

}
