package com.devnity.devnity.domain.introduction.exception;

import com.devnity.devnity.common.error.exception.InvalidValueException;

public class InvalidParentCommentException extends InvalidValueException {

  public InvalidParentCommentException(String value) {
    super(value);
  }
}
