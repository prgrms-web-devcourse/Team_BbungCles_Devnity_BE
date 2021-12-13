package com.devnity.devnity.domain.introduction.respository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.introduction.entity.IntroductionLike;
import com.devnity.devnity.setting.config.TestConfig;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@DataJpaTest
class IntroductionLikeRepositoryTest {

  @Autowired private IntroductionLikeRepository introductionLikeRepository;
  
  @DisplayName("IntroductionLike이 저장된다")
  @Test
  public void testSaveIntroductionLike() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);

    // when
    introductionLikeRepository.save(introductionLike);

    // then
    List<IntroductionLike> all = introductionLikeRepository.findAll();
    assertThat(all).isNotEmpty();
  }
  
  @DisplayName("existsByUserIdAndIntroductionId 테스트")
  @Test 
  public void testExistByUserIdIntroductionId() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);
    introductionLikeRepository.save(introductionLike);

    // when
    boolean exists = introductionLikeRepository.existsByUserIdAndIntroductionId(
      introductionLike.getUserId(), introductionLike.getIntroductionId());

    // then
    assertThat(exists).isTrue();
  }

  @DisplayName("findByUserIdAndIntroductionId 테스트")
  @Test
  public void testFindByUserIdIntroductionId() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);
    introductionLikeRepository.save(introductionLike);

    // when
    Optional<IntroductionLike> found = introductionLikeRepository.findByUserIdAndIntroductionId(
      introductionLike.getUserId(), introductionLike.getIntroductionId());

    // then
    assertThat(found).isPresent();
  }
}