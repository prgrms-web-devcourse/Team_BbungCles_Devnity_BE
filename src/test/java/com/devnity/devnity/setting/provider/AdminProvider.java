package com.devnity.devnity.setting.provider;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Component
public class AdminProvider {

  private final CourseRepository courseRepository;
  private final GenerationRepository generationRepository;

  public Generation createGeneration(int sequence) {
    return generationRepository.save(new Generation(sequence));
  }

  public Course createCourse(String name) {
    return courseRepository.save(new Course(name));
  }

}
