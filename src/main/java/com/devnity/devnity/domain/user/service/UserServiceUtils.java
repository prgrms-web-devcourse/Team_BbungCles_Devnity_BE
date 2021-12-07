package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Group;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.GroupRepository;
import org.springframework.stereotype.Component;

@Component
public class UserServiceUtils {

  public static Group findGroup(GroupRepository groupRepository, UserRole role) {
    return groupRepository.findByName(role == UserRole.MANAGER ? "ADMIN_GROUP" : "USER_GROUP");
  }

  public static Generation findGeneration(GenerationRepository generationRepository, int sequence) {
    return generationRepository.findBySequence(sequence);
  }

  public static Course findCourse(CourseRepository courseRepository, String name) {
    return courseRepository.findByName(name);
  }
}
