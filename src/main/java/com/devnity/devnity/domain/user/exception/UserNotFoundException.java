package com.devnity.devnity.domain.user.exception;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
