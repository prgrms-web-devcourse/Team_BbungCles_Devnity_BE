package com.devnity.devnity.domain.introduction.exception;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;

public class IntroductionLikeNotFoundException extends EntityNotFoundException {

  public IntroductionLikeNotFoundException(String message) {
    super(message);
  }
}
