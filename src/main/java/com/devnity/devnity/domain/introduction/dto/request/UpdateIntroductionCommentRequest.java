package com.devnity.devnity.domain.introduction.dto.request;

import com.devnity.devnity.common.utils.annotation.Comment;
import javax.validation.constraints.NotBlank;
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
