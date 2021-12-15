package com.devnity.devnity.domain.mapgakco.dto.mapgakco.response;

import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.response.MapgakcoCommentResponse;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MapgakcoDetailResponse {

  private MapgakcoResponse mapgakco;
  private SimpleUserInfoDto writer;
  private List<SimpleUserInfoDto> applicants;
  private List<MapgakcoCommentResponse> comments;

  @Builder
  public MapgakcoDetailResponse(
    MapgakcoResponse mapgakco,
    SimpleUserInfoDto writer,
    List<SimpleUserInfoDto> applicants,
    List<MapgakcoCommentResponse> comments
  ) {
    this.mapgakco = mapgakco;
    this.writer = writer;
    this.applicants = applicants;
    this.comments = comments;
  }

  public static MapgakcoDetailResponse of(
    MapgakcoResponse mapgakco,
    SimpleUserInfoDto writer,
    List<SimpleUserInfoDto> applicants,
    List<MapgakcoCommentResponse> comments
  ) {
    return MapgakcoDetailResponse.builder()
      .mapgakco(mapgakco)
      .writer(writer)
      .applicants(applicants)
      .comments(comments)
      .build();
  }

}