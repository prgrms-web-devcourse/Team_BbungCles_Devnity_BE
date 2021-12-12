package com.devnity.devnity.domain.introduction.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.devnity.devnity.domain.introduction.exception.InvalidParentCommentException;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IntroductionCommentTest {


  @DisplayName("댓글을 생성할 수 있다")
  @Test
  public void testCreateComment() throws Exception {
    // given
    User user = User.builder().email("email").password("password").role(UserRole.STUDENT).build();

    Introduction introduction = user.getIntroduction();

    // when
    String content = "content";
    IntroductionComment comment = IntroductionComment.of(content, user, introduction);

    // then
    assertThat(comment).isNotNull();
    assertThat(comment.getUser()).isEqualTo(user);
    assertThat(comment.getIntroduction()).isEqualTo(introduction);
    assertThat(comment.getContent()).isEqualTo(content);
    assertThat(comment.getParent()).isNull();
  }

  @DisplayName("대댓글을 생성할 수 있다")
  @Test
  public void testCreateSubComment() throws Exception {
    // given
    User user = User.builder().email("email").password("password").role(UserRole.STUDENT).build();

    Introduction introduction = user.getIntroduction();
    String content = "content";
    IntroductionComment parent = IntroductionComment.of(content, user, introduction);

    // when
    IntroductionComment comment = IntroductionComment.of(content, user, introduction, parent);

    // then
    assertThat(comment).isNotNull();
    assertThat(comment.getUser()).isEqualTo(user);
    assertThat(comment.getIntroduction()).isEqualTo(introduction);
    assertThat(comment.getContent()).isEqualTo(content);
    assertThat(comment.getParent()).isEqualTo(parent);
  }

  @DisplayName("대댓글 최대 깊이는 1이다")
  @Test
  public void testCreateSubCommentOverDepth1() throws Exception {
    // given
    User user = User.builder().email("email").password("password").role(UserRole.STUDENT).build();

    Introduction introduction = user.getIntroduction();
    String content = "content";
    IntroductionComment grandParent = IntroductionComment.of(content, user, introduction);
    IntroductionComment parent = IntroductionComment.of(content, user, introduction, grandParent);

    // when // then
    assertThatThrownBy(() -> IntroductionComment.of(content, user, introduction, parent))
        .isInstanceOf(InvalidParentCommentException.class);
  }


}