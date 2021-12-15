package com.devnity.devnity.domain.gather.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateGatherCommentRequest {

  private Long parentId;
  private String content;

  @Builder
  public CreateGatherCommentRequest(Long parentId, String content) {
    this.parentId = parentId;
    this.content = content;
  }
}
