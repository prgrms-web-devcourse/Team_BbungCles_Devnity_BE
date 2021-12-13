package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface IntroductionCommentRepository extends JpaRepository<IntroductionComment, Long> {

  Optional<IntroductionComment> findByIdAndUserIdAndIntroductionId(
      @Param("id") Long id,
      @Param("userId") Long userId,
      @Param("introductionId") Long introductionId);
}
