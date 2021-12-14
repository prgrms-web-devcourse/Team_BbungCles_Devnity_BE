package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.respository.custom.IntroductionCommentCustomRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IntroductionCommentRepository
    extends JpaRepository<IntroductionComment, Long>, IntroductionCommentCustomRepository {


  Optional<IntroductionComment> findByIdAndUserIdAndIntroductionId(
      @Param("id") Long id,
      @Param("userId") Long userId,
      @Param("introductionId") Long introductionId);

  @Query(
      "select count(ic.id) from IntroductionComment ic where ic.introduction.id = :introductionId")
  long countBy(@Param("introductionId") Long introductionId);
}
