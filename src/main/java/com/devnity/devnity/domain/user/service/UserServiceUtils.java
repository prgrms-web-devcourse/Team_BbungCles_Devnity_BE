package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;

public class UserServiceUtils {
  public static Generation findGeneration(GenerationRepository generationRepository, int sequence) {
    return generationRepository.findBySequence(sequence)
      .orElseThrow(() -> new IllegalArgumentException(String.format("There is no generation for sequence = %d", sequence)));
  }

  public static Course findCourse(CourseRepository courseRepository, String name) {
    return courseRepository.findByName(name)
      .orElseThrow(() -> new IllegalArgumentException(String.format("There is no course for name = %s", name)));
  }

  public static User findUser(UserRepository userRepository, Long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException(
        String.format("There is no user for id = %d", userId)));
  }
}
