package com.devnity.devnity.domain.introduction.dto.request;

import com.devnity.devnity.web.common.utils.annotation.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateIntroductionCommentRequest {

  @Comment
  String content;

  public UpdateIntroductionCommentRequest(String content) {
    this.content = content;
  }
}
