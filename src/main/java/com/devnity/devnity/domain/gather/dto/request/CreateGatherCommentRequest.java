package com.devnity.devnity.domain.gather.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateGatherCommentRequest {

  private Long parentId;

  private String content;

}
