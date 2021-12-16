package com.devnity.devnity.domain.introduction.service;

import static com.devnity.devnity.common.error.exception.ErrorCode.INTRODUCTION_NOT_FOUND;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;

public class IntroductionServiceUtils {

  public static Introduction findIntroductionByIdAndUserId(
      IntroductionRepository introductionRepository, Long introductionId, Long userId) {
    return introductionRepository
      .findIntroductionByIdAndUserId(introductionId, userId)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format(
              "There is no introduction. userId=%d, introductionId=%d",
              userId, introductionId), INTRODUCTION_NOT_FOUND));
  }

  public static Introduction findIntroductionById(
      IntroductionRepository introductionRepository, Long introductionId) {
    return introductionRepository
      .findById(introductionId)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format(
              "There is no introduction. introductionId=%d",
              introductionId), INTRODUCTION_NOT_FOUND));
  }
}
