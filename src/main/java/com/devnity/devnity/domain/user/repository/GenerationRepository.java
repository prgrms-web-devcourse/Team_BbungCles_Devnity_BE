package com.devnity.devnity.domain.user.repository;

import com.devnity.devnity.domain.user.entity.Generation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerationRepository extends JpaRepository<Generation, Long> {

  Optional<Generation> findBySequence(int sequence);
}
