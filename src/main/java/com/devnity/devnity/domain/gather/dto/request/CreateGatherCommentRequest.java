package com.devnity.devnity.domain.gather.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
public class CreateGatherCommentRequest {

  private Long parentId;
  private String content;

}
