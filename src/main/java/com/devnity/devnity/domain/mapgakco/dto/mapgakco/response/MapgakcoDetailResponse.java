package com.devnity.devnity.domain.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.response.MapgakcoCommentResponse;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
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
public class MapgakcoDetailResponse {

  private MapgakcoResponse mapgakco;
  private SimpleUserInfoDto writer;
  private List<SimpleUserInfoDto> applicants;
  private List<MapgakcoCommentResponse> comments;

}