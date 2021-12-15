package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateGatherCommentResponse {

  private Long commentId;
  private Long parentId;

  public static CreateGatherCommentResponse of(GatherComment comment) {
    CreateGatherCommentResponse.CreateGatherCommentResponseBuilder builder
      = CreateGatherCommentResponse.builder();
    builder.commentId(comment.getId());
    if(comment.getParent() != null)
      builder.parentId(comment.getParent().getId());
    return builder.build();
  }

}