package com.devnity.devnity.domain.introduction.exception;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;

public class IntroductionCommentNotFoundException extends EntityNotFoundException {

  public IntroductionCommentNotFoundException(String message) {
    super(message);
  }
}
