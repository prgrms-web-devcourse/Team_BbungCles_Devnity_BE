package com.devnity.devnity.web.introduction.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DeleteIntroductionCommentResponse {

  private String content;

  public DeleteIntroductionCommentResponse(String content) {
    this.content = content;
  }
}
