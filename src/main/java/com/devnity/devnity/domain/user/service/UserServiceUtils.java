package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.exception.CourseNotFoundException;
import com.devnity.devnity.domain.user.exception.GenerationNotFoundException;
import com.devnity.devnity.domain.user.exception.UserNotFoundException;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.mysema.commons.lang.Assert;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserServiceUtils {
  public static Generation findGeneration(GenerationRepository generationRepository, int sequence) {
    return generationRepository.findBySequence(sequence)
      .orElseThrow(() -> new GenerationNotFoundException(String.format("There is no generation for sequence = %d", sequence)));
  }

  public static Course findCourse(CourseRepository courseRepository, String name) {
    return courseRepository.findByName(name)
      .orElseThrow(() -> new CourseNotFoundException(String.format("There is no course for name = %s", name)));
  }

  public static User findUser(UserRepository userRepository, Long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new UserNotFoundException(
        String.format("There is no user for id = %d", userId)));
  }

  public static <T> void notNull(T object, String message) {
    if (Objects.isNull(object)) {
      throw new InvalidValueException(message);
    }
  }
}
