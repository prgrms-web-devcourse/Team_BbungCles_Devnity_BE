package com.devnity.devnity.domain.introduction.respository.custom;

import static com.devnity.devnity.domain.introduction.entity.QIntroduction.introduction;
import static com.devnity.devnity.domain.introduction.entity.QIntroductionComment.introductionComment;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.entity.QIntroduction;
import com.devnity.devnity.domain.introduction.entity.QIntroductionComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntroductionCommentCustomRepositoryImpl implements
  IntroductionCommentCustomRepository {

  private JPAQueryFactory jpaQueryFactory;

  @Override
  public List<IntroductionComment> findAllParentsByDesc(Long introductionId) {
    return jpaQueryFactory
        .selectFrom(introductionComment)
        .where(
            introductionComment.introduction.id.eq(introductionId),
            introductionComment.parent.isNull())
        .orderBy(introductionComment.id.desc())
        .fetch();
  }

  @Override
  public List<IntroductionComment> findAllChildrenByDesc(IntroductionComment parent) {
    return jpaQueryFactory
      .selectFrom(introductionComment)
      .where(
        introductionComment.parent.eq(parent))
      .orderBy(introductionComment.id.desc())
      .fetch();
  }
}
