package com.devnity.devnity.domain.introduction.exception;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;

public class IntroductionNotFoundException extends EntityNotFoundException {

  public IntroductionNotFoundException(String message) {
    super(message);
  }
}
