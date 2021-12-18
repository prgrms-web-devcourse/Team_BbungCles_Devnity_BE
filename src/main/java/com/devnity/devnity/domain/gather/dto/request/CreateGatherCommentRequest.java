package com.devnity.devnity.domain.gather.dto.request;

import com.devnity.devnity.common.utils.annotation.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class CreateGatherCommentRequest {

  private Long parentId;

  @Comment
  private String content;

}
