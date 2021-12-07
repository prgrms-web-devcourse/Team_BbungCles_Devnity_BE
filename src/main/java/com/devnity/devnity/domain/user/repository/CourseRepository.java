package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

  Course findByName(String name);
}
