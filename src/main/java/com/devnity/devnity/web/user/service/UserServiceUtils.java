package com.devnity.devnity.web.user.service;

import static com.devnity.devnity.common.error.exception.ErrorCode.ENTITY_NOT_FOUND;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {
  public static Generation findGeneration(GenerationRepository generationRepository, int sequence) {
    return generationRepository.findBySequence(sequence)
      .orElseThrow(() -> new EntityNotFoundException(String.format("There is no generation for sequence = %d", sequence), ENTITY_NOT_FOUND));
  }

  public static Course findCourse(CourseRepository courseRepository, String name) {
    return courseRepository.findByName(name)
      .orElseThrow(() -> new EntityNotFoundException(String.format("There is no course for name = %s", name), ENTITY_NOT_FOUND));
  }

  public static <T> void notNull(T object, String message) {
    if (Objects.isNull(object)) {
      throw new InvalidValueException(message, ErrorCode.INVALID_INPUT_VALUE);
    }
  }
}
