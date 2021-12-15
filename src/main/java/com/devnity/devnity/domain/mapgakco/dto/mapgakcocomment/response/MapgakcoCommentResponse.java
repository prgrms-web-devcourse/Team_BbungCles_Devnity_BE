package com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.response;

import com.devnity.devnity.domain.mapgakco.entity.MapgakcoCommentStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoCommentResponse {

  private Long commentId;
  private String content;
  private MapgakcoCommentStatus status;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdAt;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime updatedAt;
  private SimpleUserInfoDto writer;
  private MapgakcoCommentResponse child;

}
