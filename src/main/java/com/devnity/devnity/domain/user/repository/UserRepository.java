package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
  Optional<User> findUserByEmail(String email);

  boolean existsByEmail(String email);
}
