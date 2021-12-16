package com.devnity.devnity.domain.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoPageResponse {

  private List<SimpleMapgakcoInfoDto> mapgakcos;
  private Boolean hasNext;

}
