package com.devnity.devnity.web.mapgakco.dto.mapgakcocomment.response;

import com.devnity.devnity.domain.mapgakco.entity.MapgakcoCommentStatus;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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
public class MapgakcoCommentResponse {

  private Long commentId;
  private String content;
  private MapgakcoCommentStatus status;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedAt;
  private SimpleUserInfoDto author;
  private List<MapgakcoCommentResponse> children;

}
