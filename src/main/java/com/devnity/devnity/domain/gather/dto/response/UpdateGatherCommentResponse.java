package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateGatherCommentResponse {

  private LocalDateTime modifiedAt;

  public static UpdateGatherCommentResponse of(GatherComment comment) {
    return UpdateGatherCommentResponse.builder()
      .modifiedAt(comment.getModifiedAt())
      .build();
  }

}
