package com.devnity.devnity.domain.introduction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.introduction.entity.IntroductionLike;
import com.devnity.devnity.domain.introduction.respository.IntroductionLikeRepository;
import java.util.Optional;

import com.devnity.devnity.web.introduction.service.IntroductionLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IntroductionLikeServiceTest {

  @InjectMocks private IntroductionLikeService introductionLikeService;

  @Mock private IntroductionLikeRepository introductionLikeRepository;
  
  @DisplayName("좋아요가 생성된다")
  @Test 
  public void testLike() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);

    given(introductionLikeRepository.existsByUserIdAndIntroductionId(anyLong(), anyLong()))
      .willReturn(false);

    // when
    boolean result = introductionLikeService.like(
      introductionLike.getUserId(), introductionLike.getIntroductionId());

    // then
    assertThat(result).isTrue();
  }

  @DisplayName("좋아요를 중복 생성할 수 없다")
  @Test
  public void testLikeDuplicate() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);

    given(introductionLikeRepository.existsByUserIdAndIntroductionId(anyLong(), anyLong()))
      .willReturn(true);

    // when // then
    assertThatThrownBy(() -> introductionLikeService.like(
      introductionLike.getUserId(), introductionLike.getIntroductionId())).isInstanceOf(
      InvalidValueException.class);
  }

  @DisplayName("좋아요를 삭제한다")
  @Test
  public void testRemoveLike() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);

    given(introductionLikeRepository.findByUserIdAndIntroductionId(anyLong(), anyLong()))
      .willReturn(Optional.of(introductionLike));

    // when
    boolean result = introductionLikeService.removeLike(
      introductionLike.getUserId(), introductionLike.getIntroductionId());

    // then;
    assertThat(result).isFalse();
  }
  
  @DisplayName("좋아요가 존재 하지 않으면 제거할 수 없다")
  @Test
  public void testRemoveLikeNotExist() throws Exception {
    // given
    IntroductionLike introductionLike = new IntroductionLike(1L, 1L);

    given(introductionLikeRepository.findByUserIdAndIntroductionId(anyLong(), anyLong()))
      .willReturn(Optional.empty());

    // when // then
    assertThatThrownBy(
            () ->
                introductionLikeService.removeLike(
                    introductionLike.getUserId(), introductionLike.getIntroductionId()))
        .isInstanceOf(EntityNotFoundException.class);
  }
}