package com.devnity.devnity.domain.introduction.exception;

import com.devnity.devnity.common.error.exception.InvalidValueException;

public class IntroductionLIkeDuplicateException extends InvalidValueException {

  public IntroductionLIkeDuplicateException(String value) {
    super(value);
  }
}
