package com.devnity.devnity.domain.introduction.respository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({TestConfig.class, UserProvider.class})
@DataJpaTest
class IntroductionCommentRepositoryTest {

  @Autowired private IntroductionCommentRepository introductionCommentRepository;

  @Autowired private UserProvider userProvider;

  @DisplayName("findByIdAndUserIdAndIntroductionId 테스트")
  @Test
  public void testFindByIdAndUserIdAndIntroductionId() throws Exception {
    //given
    User user = userProvider.createUser();
    String content = "content";
    IntroductionComment comment = IntroductionComment.of(content, user, user.getIntroduction());

    // when
    introductionCommentRepository.save(comment);
    // then
    Optional<IntroductionComment> found = introductionCommentRepository.findByIdAndUserIdAndIntroductionId(
      comment.getId(), user.getId(), user.getIntroduction().getId());

    assertThat(found).isPresent();
    assertThat(found.get().getContent()).isEqualTo(content);
  }

  @DisplayName("findByIdAndUserIdAndIntroductionId 실패 테스트")
  @Test
  public void testFindByIdAndUserIdAndIntroductionIdFail() throws Exception {
    //given
    User user = userProvider.createUser();
    String content = "content";
    IntroductionComment comment = IntroductionComment.of(content, user, user.getIntroduction());

    // when
    introductionCommentRepository.save(comment);
    // then
    Optional<IntroductionComment> found = introductionCommentRepository.findByIdAndUserIdAndIntroductionId(
      comment.getId(), user.getId(), user.getIntroduction().getId() + 100L);

    assertThat(found).isEmpty();
  }
}