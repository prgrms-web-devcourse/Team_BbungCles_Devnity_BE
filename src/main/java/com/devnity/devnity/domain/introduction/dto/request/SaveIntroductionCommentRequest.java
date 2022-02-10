package com.devnity.devnity.domain.introduction.dto.request;

import com.devnity.devnity.web.common.utils.annotation.Comment;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class SaveIntroductionCommentRequest {

  Long parentId;

  @Comment
  String content;

  @Builder
  public SaveIntroductionCommentRequest(Long parentId, String content) {
    this.parentId = parentId;
    this.content = content;
  }

  public IntroductionComment toEntity(
      User user, Introduction introduction, IntroductionComment parent) {

    if (parent == null) {
      return IntroductionComment.of(content, user, introduction);
    }

    return IntroductionComment.of(content, user, introduction, parent);
  }

}
