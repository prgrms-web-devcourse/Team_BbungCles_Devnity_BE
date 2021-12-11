package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.User;
import java.util.List;

public interface UserCustomRepository {

  List<User> findAllByCourseAndGenerationLimit(User user, int size);
}
