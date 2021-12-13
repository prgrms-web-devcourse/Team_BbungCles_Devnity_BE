package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.IntroductionLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IntroductionLikeRepository extends JpaRepository<IntroductionLike, Long> {
  boolean existsByUserIdAndIntroductionId(
      @Param("userId") Long userId, @Param("IntroductionId") Long IntroductionId);

  Optional<IntroductionLike> findByUserIdAndIntroductionId(
    @Param("userId") Long userId, @Param("IntroductionId") Long IntroductionId);
}
