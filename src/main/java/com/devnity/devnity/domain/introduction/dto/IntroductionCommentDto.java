package com.devnity.devnity.domain.introduction.dto;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.entity.IntroductionCommentStatus;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class IntroductionCommentDto {

  private Long commentId;
  private String content;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime updatedAt;

  private IntroductionCommentStatus status;

  private SimpleUserInfoDto writer;

  private List<IntroductionCommentDto> children;

  @Builder
  public IntroductionCommentDto(Long commentId, String content, LocalDateTime createdAt,
    LocalDateTime updatedAt,
    IntroductionCommentStatus status, SimpleUserInfoDto writer,
    List<IntroductionCommentDto> children) {
    this.commentId = commentId;
    this.content = content;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.status = status;
    this.writer = writer;
    this.children = children;
  }

  public static IntroductionCommentDto of(
      IntroductionComment comment,
      List<IntroductionCommentDto> children,
      SimpleUserInfoDto writer) {

    return IntroductionCommentDto.builder()
        .commentId(comment.getId())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getModifiedAt())
        .status(comment.getStatus())
        .writer(writer)
        .children(children)
        .build();
  }

  public static IntroductionCommentDto of(
    IntroductionComment comment,
    SimpleUserInfoDto writer) {

    return IntroductionCommentDto.builder()
      .commentId(comment.getId())
      .content(comment.getContent())
      .createdAt(comment.getCreatedAt())
      .updatedAt(comment.getModifiedAt())
      .status(comment.getStatus())
      .writer(writer)
      .build();
  }
}
