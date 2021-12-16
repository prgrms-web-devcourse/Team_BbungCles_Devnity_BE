package com.devnity.devnity.domain.mapgakco.dto.mapgakco.request;

import com.devnity.devnity.domain.mapgakco.dto.CoordinateDto;
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

  private CoordinateDto lastCoordinate;
  private CoordinateDto currnetCoordinate;

}
