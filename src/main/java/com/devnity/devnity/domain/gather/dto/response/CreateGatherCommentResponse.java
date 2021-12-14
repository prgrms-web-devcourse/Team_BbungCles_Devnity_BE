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
    return CreateGatherCommentResponse.builder()
      .commentId(comment.getId())
      .build();
  }

  public static CreateGatherCommentResponse of(GatherComment comment, GatherComment parent) {
    return CreateGatherCommentResponse.builder()
      .commentId(comment.getId())
      .parentId(parent.getId())
      .build();
  }

}
