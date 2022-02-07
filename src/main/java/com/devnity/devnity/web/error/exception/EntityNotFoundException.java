package com.devnity.devnity.web.error.exception;

public class EntityNotFoundException extends BusinessException {

  public EntityNotFoundException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public EntityNotFoundException(String message) {
    super(message, ErrorCode.ENTITY_NOT_FOUND);
  }

}
