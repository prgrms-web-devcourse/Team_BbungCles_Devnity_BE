package com.devnity.devnity.domain.user.exception;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;

public class CourseNotFoundException extends EntityNotFoundException {

  public CourseNotFoundException(String message) {
    super(message);
  }
}
