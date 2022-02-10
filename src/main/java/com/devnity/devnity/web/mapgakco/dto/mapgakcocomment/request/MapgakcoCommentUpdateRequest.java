package com.devnity.devnity.web.mapgakco.dto.mapgakcocomment.request;

import com.devnity.devnity.web.common.utils.annotation.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoCommentUpdateRequest {

  @Comment
  private String content;

}
