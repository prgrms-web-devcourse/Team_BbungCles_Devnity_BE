package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

  @Query("select i from Introduction i where i.id = :id and i.user.id = :userId and i.status = 'POSTED'")
  Optional<Introduction> findByIdAndUserId(Long id, Long userId);
}
