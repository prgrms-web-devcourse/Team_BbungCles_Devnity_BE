package com.devnity.devnity.web.error.exception;

public class InvalidValueException extends BusinessException {

  public InvalidValueException(String value) {
    super(value, ErrorCode.INVALID_INPUT_VALUE);
  }

  public InvalidValueException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidValueException(String value, ErrorCode errorCode) {
    super(value, errorCode);
  }
}
