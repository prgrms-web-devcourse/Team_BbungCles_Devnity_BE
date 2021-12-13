package com.devnity.devnity.domain.introduction.entity;

import com.devnity.devnity.domain.introduction.exception.IntroductionCommentAlreadyDeletedException;
import com.devnity.devnity.domain.introduction.exception.InvalidParentCommentException;
import com.devnity.devnity.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "introduction_comment")
public class IntroductionComment {

  private static final String DELETED_CONTENT = "[삭제된 댓글입니다]";

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "introduction_id")
  private Introduction introduction;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private IntroductionComment parent;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private IntroductionCommentStatus status;

  @Builder
  private IntroductionComment(String content, User user,
      Introduction introduction, IntroductionComment parent) {
    this.content = content;
    this.user = user;
    this.introduction = introduction;
    this.parent = parent;
    this.status = IntroductionCommentStatus.POSTED;
  }

  public static IntroductionComment of(
      String content, User user, Introduction introduction, IntroductionComment parent) {

    validate(parent);

    return IntroductionComment.builder()
        .parent(parent)
        .introduction(introduction)
        .user(user)
        .content(content)
        .build();
  }

  private static void validate(IntroductionComment parent) {
    if (parent.isChild())
      throw new InvalidParentCommentException(
          String.format("comment is child. id = %d", parent.getId()));
  }

  public static IntroductionComment of(
      String content, User user, Introduction introduction) {
    return IntroductionComment.builder()
        .introduction(introduction)
        .user(user)
        .content(content)
        .build();
  }

  //== 비즈니스 메서드 ==//
  public void updateContent(String content) {
    this.content = content;
  }

  public void delete() {
    if (isDeleted()) {
      throw new IntroductionCommentAlreadyDeletedException(
          String.format("Comment is already deleted. id = %d", id));
    }

    status = IntroductionCommentStatus.DELETED;
  }

  public String getContent() {
    if (isDeleted()) {
      return DELETED_CONTENT;
    }
    return this.content;
  }

  private boolean isChild() {
    return this.parent != null;
  }

  private boolean isDeleted() {
    return this.status.equals(IntroductionCommentStatus.DELETED);
  }
}
