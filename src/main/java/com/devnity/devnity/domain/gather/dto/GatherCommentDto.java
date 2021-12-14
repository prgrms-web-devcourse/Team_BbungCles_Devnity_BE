package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherCommentDto {

  private Long commentId;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private GatherCommentStatus status;
  private SimpleUserInfoDto author;
  private List<GatherCommentDto> subComment;

  public static GatherCommentDto of(GatherComment comment) {
    GatherCommentDto.builder()
      .commentId(comment.getId())
      .content(comment.getContent())
      .createdAt(comment.getCreatedAt())
      .modifiedAt(comment.getModifiedAt())
      .status(comment.getStatus())
      .author(SimpleUserInfoDto.of(comment.getUser()))
      .subComment(

      )
      .build();

  }

}
