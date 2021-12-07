package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {

  Group findByName(String name);
}
