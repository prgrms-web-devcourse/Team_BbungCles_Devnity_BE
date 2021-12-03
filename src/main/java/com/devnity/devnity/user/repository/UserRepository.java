package com.devnity.devnity.user.repository;

import com.devnity.devnity.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
