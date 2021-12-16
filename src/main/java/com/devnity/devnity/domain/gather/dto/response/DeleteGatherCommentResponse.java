package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeleteGatherCommentResponse {

  private String content;

  public static DeleteGatherCommentResponse of(GatherComment comment){
    return DeleteGatherCommentResponse.builder()
      .content(comment.getContent())
      .build();
  }

}
