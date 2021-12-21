package com.devnity.devnity.domain.introduction.respository.custom;

import static com.devnity.devnity.domain.introduction.entity.QIntroductionComment.introductionComment;

import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntroductionCommentCustomRepositoryImpl implements
  IntroductionCommentCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

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
      .orderBy(introductionComment.id.asc())
      .fetch();
  }
}
