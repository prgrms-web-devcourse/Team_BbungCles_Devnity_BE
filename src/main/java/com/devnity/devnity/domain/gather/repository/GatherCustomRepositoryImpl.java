package com.devnity.devnity.domain.gather.repository;

import static com.devnity.devnity.domain.gather.entity.QGather.gather;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
  public List<Gather> findByPaging(GatherCategory category, Long lastId, int size) {
    return jpaQueryFactory
      .select(
        ExpressionUtils.as(
          JPAExpressions
            .selectFrom(gather)
            .where(
              gather.category.eq(category),
              gather.status.ne(GatherStatus.DELETED)
            )
            .orderBy(
              provideStatusOrder(),
              gather.id.desc()
            ),
          "subQuery"
        )
      )
      .where(
        gather.id.lt(lastId)
      )
      .limit(size)
      .fetch();
  }

  private OrderSpecifier<Integer> provideStatusOrder() {
    NumberExpression<Integer> cases = new CaseBuilder()
      .when(gather.status.eq(GatherStatus.GATHERING))
      .then(1)
      .otherwise(2);
    return new OrderSpecifier<>(Order.ASC, cases);
  }

}

