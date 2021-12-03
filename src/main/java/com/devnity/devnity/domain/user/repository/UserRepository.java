package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
