package com.devnity.devnity.domain.introduction.service;

import com.devnity.devnity.domain.introduction.entity.IntroductionLike;
import com.devnity.devnity.domain.introduction.exception.IntroductionLIkeDuplicateException;
import com.devnity.devnity.domain.introduction.exception.IntroductionLikeNotFoundException;
import com.devnity.devnity.domain.introduction.respository.IntroductionLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class IntroductionLikeService {

  private final IntroductionLikeRepository introductionLikeRepository;

  @Transactional
  public boolean like(Long userId, Long introductionId) {
    if (existsBy(userId, introductionId)) {
      throw new IntroductionLIkeDuplicateException(
          String.format(
              "IntroductionLike already exist. userId = %d, introductionId = %d",
              userId, introductionId));
    }

    introductionLikeRepository.save(new IntroductionLike(userId, introductionId));

    return true;
  }

  private boolean existsBy(Long userId, Long introductionId) {
    return introductionLikeRepository.existsByUserIdAndIntroductionId(userId, introductionId);
  }

  @Transactional
  public boolean removeLike(Long userId, Long introductionId) {
    IntroductionLike introductionLike = introductionLikeRepository
      .findByUserIdAndIntroductionId(userId, introductionId)
      .orElseThrow(
        () ->
          new IntroductionLikeNotFoundException(
            String.format(
              "There is no IntroductionLike. userId = %d, introductionId = %d",
              userId, introductionId)));

    introductionLikeRepository.delete(introductionLike);

    return false;
  }
}
