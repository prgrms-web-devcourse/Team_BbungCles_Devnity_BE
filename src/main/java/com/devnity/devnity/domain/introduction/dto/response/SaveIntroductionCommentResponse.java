package com.devnity.devnity.domain.introduction.dto.response;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SaveIntroductionCommentResponse {

  Long commentId;
  Long parentId;

  @Builder
  private SaveIntroductionCommentResponse(Long commentId, Long parentId) {
    this.commentId = commentId;
    this.parentId = parentId;
  }

  public static SaveIntroductionCommentResponse of(
      IntroductionComment comment, IntroductionComment parent) {

    SaveIntroductionCommentResponseBuilder builder =
        SaveIntroductionCommentResponse.builder().commentId(comment.getId());

    if (parent != null) {
      builder.parentId(parent.getId());
    }

    return builder.build();
  }
}
