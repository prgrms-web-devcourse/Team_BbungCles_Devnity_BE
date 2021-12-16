package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateGatherCommentResponse {

  private Long commentId;
  private Long parentId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  public static CreateGatherCommentResponse of(GatherComment comment) {
    CreateGatherCommentResponse.CreateGatherCommentResponseBuilder builder
      = CreateGatherCommentResponse.builder();
    builder
      .commentId(comment.getId())
      .createdAt(comment.getCreatedAt());

    if(comment.getParent() != null)
      builder.parentId(comment.getParent().getId());
    return builder.build();
  }

}
