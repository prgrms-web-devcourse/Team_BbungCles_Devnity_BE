package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;

public interface UserCustomRepository {

  List<User> findAllByCourseAndGenerationLimit(Course course, Generation generation, int size);
}
