package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherCommentDto {

  private Long commentId;
  private String content;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedAt;

  private GatherCommentStatus status;
  private SimpleUserInfoDto author;
  private List<GatherChildCommentDto> children;

  public static GatherCommentDto of(GatherComment comment, List<GatherChildCommentDto> children) {
    return GatherCommentDto.builder()
      .commentId(comment.getId())
      .content(comment.getContent())
      .createdAt(comment.getCreatedAt())
      .modifiedAt(comment.getModifiedAt())
      .status(comment.getStatus())
      .author(SimpleUserInfoDto.of(comment.getUser()))
      .children(children)
      .build();
  }

}
