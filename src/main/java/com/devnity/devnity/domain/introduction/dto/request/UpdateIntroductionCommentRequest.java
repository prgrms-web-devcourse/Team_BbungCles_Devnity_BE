package com.devnity.devnity.domain.introduction.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UpdateIntroductionCommentRequest {

  @NotBlank
  String content;

  public UpdateIntroductionCommentRequest(String content) {
    this.content = content;
  }
}
