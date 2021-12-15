package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GatherSubCommentDto {

  private Long commentId;
  private Long parentId;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private SimpleUserInfoDto author;

  public static GatherSubCommentDto of(GatherComment subComment) {
    return GatherSubCommentDto.builder()
      .commentId(subComment.getId())
      .parentId(subComment.getParent().getId())
      .content(subComment.getContent())
      .createdAt(subComment.getCreatedAt())
      .modifiedAt(subComment.getModifiedAt())
      .author(SimpleUserInfoDto.of(subComment.getUser()))
      .build();
  }
}
