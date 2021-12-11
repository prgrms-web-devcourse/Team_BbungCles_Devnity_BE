package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import java.util.Optional;

public interface IntroductionCustomRepository {
  Optional<Introduction> findIntroductionByIdAndUserId(Long introductionId, Long userId);
}
