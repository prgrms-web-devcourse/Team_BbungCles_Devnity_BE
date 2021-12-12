package com.devnity.devnity.domain.introduction.dto.response;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SaveIntroductionCommentResponse {

  Long commentId;
  Long parentId;

  public SaveIntroductionCommentResponse(IntroductionComment comment, IntroductionComment parent) {
    this.commentId = comment.getId();
    this.parentId = parent == null ? null : parent.getId();
  }
}
