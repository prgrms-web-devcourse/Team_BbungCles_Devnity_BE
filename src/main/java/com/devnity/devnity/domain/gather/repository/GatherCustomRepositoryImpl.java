package com.devnity.devnity.domain.gather.repository;

import static com.devnity.devnity.domain.gather.entity.QGather.gather;
import static com.devnity.devnity.domain.gather.entity.QGatherApplicant.gatherApplicant;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.QGatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GatherCustomRepositoryImpl implements GatherCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Gather> findByPaging(GatherCategory category, List<GatherStatus> statuses, Long lastId, int size) {
    return jpaQueryFactory
      .selectFrom(gather)
      .where(
        eqCategory(category),
        gather.status.in(statuses),
        ltLastId(lastId)
      )
      .orderBy(
        gather.id.desc()
      )
      .limit(size)
      .fetch();
  }

  private BooleanExpression eqCategory(GatherCategory category) {
    if (category == null) {
      return null;
    }
    return gather.category.eq(category);
  }

  private BooleanExpression ltLastId(Long lastId) {
    if (lastId == null) {
      return null;
    }
    return gather.id.lt(lastId);
  }

  @Override
  public List<Gather> findForSuggest(int size) {
    return jpaQueryFactory
      .selectFrom(gather)
      .where(
        gather.status.ne(GatherStatus.DELETED)
      )
      .orderBy(
        gather.id.desc()
      )
      .limit(size)
      .fetch();
  }

  @Override
  public List<Gather> findGathersHostedBy(User host) {
    return jpaQueryFactory
      .selectFrom(gather)
      .where(
        gather.user.eq(host),
        gather.status.ne(GatherStatus.DELETED)
      )
      .orderBy(gather.id.desc())
      .fetch();
  }

  @Override
  public List<Gather> findGathersAppliedBy(User applicant) {
    return jpaQueryFactory
      .selectFrom(gather)
      .join(gather.applicants, gatherApplicant)
      .where(
        gatherApplicant.user.eq(applicant),
        gather.status.ne(GatherStatus.DELETED)
      )
      .orderBy(gather.id.desc())
      .fetch();
  }
}

