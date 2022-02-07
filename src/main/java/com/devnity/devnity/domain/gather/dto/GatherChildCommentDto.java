package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GatherChildCommentDto {

  private Long commentId;
  private Long parentId;
  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedAt;

  private SimpleUserInfoDto author;

  public static GatherChildCommentDto of(GatherComment subComment) {
    return GatherChildCommentDto.builder()
      .commentId(subComment.getId())
      .parentId(subComment.getParent().getId())
      .content(subComment.getContent())
      .createdAt(subComment.getCreatedAt())
      .modifiedAt(subComment.getModifiedAt())
      .author(SimpleUserInfoDto.of(subComment.getUser()))
      .build();
  }
}
