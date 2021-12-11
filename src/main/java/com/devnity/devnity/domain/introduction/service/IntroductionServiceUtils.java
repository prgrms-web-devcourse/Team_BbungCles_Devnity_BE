package com.devnity.devnity.domain.introduction.service;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.exception.IntroductionNotFoundException;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;

public class IntroductionServiceUtils {

  public static Introduction findIntroductionByIdAndUserId(
      IntroductionRepository introductionRepository, Long userId, Long introductionId) {
    return introductionRepository
      .findIntroductionByIdAndUserId(introductionId, userId)
      .orElseThrow(
        () ->
          new IntroductionNotFoundException(
            String.format(
              "There is no introduction. userId=%d, introductionId=%d",
              userId, introductionId)));
  }
}
