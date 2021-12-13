package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroductionCommentRepository extends JpaRepository<IntroductionComment, Long> {

}
