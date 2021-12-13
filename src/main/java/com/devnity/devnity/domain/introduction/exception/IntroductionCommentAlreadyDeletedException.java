package com.devnity.devnity.domain.introduction.exception;

import com.devnity.devnity.common.error.exception.InvalidValueException;

public class IntroductionCommentAlreadyDeletedException extends InvalidValueException {

  public IntroductionCommentAlreadyDeletedException(String value) {
    super(value);
  }
}
