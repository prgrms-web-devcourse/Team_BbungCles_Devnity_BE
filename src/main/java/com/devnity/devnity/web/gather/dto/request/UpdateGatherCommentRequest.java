package com.devnity.devnity.web.gather.dto.request;

import com.devnity.devnity.web.common.utils.annotation.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class UpdateGatherCommentRequest {

  @Comment
  private String content;

}
