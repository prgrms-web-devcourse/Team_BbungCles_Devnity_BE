package com.devnity.devnity.domain.user.exception;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;

public class GenerationNotFoundException extends EntityNotFoundException {

  public GenerationNotFoundException(String message) {
    super(message);
  }
}
