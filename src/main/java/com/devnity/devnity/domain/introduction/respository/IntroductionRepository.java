package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

  Optional<Introduction> findByIdAndUserId(Long id, Long userId);
}
