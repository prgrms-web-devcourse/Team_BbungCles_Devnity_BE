package com.devnity.devnity.domain.introduction.respository.custom;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import java.util.List;

public interface IntroductionCommentCustomRepository {

  List<IntroductionComment> findAllParentsByDesc(Long introductionId);
  List<IntroductionComment> findAllChildrenByDesc(IntroductionComment parent);
}
