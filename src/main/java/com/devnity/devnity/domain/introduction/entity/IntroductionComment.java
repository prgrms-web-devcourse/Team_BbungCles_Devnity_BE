package com.devnity.devnity.domain.introduction.entity;

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
    return IntroductionComment.builder()
        .parent(parent)
        .introduction(introduction)
        .user(user)
        .content(content)
        .build();
  }

  public static IntroductionComment of(
      String content, User user, Introduction introduction) {
    return IntroductionComment.builder()
        .introduction(introduction)
        .user(user)
        .content(content)
        .build();
  }
}
