package com.devnity.devnity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long>, TestRepositoryCustom {
}
