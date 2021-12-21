package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.util.List;

public interface UserCustomRepository {

  List<User> findAllByCourseAndGenerationLimit(User user, int size);

  List<User> getAllByCourseByFilter(Course course, Generation generation, UserRole role, String name);
}
