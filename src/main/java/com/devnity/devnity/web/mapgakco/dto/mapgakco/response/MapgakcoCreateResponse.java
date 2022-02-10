package com.devnity.devnity.web.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapgakcoCreateResponse {

  private Long mapgakcoId;
  private MapgakcoStatus status;

  @Builder
  public MapgakcoCreateResponse(Long mapgakcoId, MapgakcoStatus status) {
    this.mapgakcoId = mapgakcoId;
    this.status = status;
  }

  public static MapgakcoCreateResponse of(Long mapgakcoId, MapgakcoStatus status) {
    return MapgakcoCreateResponse.builder()
      .mapgakcoId(mapgakcoId)
      .status(status)
      .build();
  }

}
