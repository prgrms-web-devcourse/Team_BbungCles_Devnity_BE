package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.Course;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

  Optional<Course> findByName(String name);
}
