package com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request;

import com.devnity.devnity.web.common.utils.annotation.Comment;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoCommentCreateRequest {

  @NotNull
  private Long parentId;
  @Comment
  private String content;

}
