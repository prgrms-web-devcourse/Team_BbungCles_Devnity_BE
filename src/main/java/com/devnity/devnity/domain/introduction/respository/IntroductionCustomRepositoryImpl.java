package com.devnity.devnity.domain.introduction.respository;

import static com.devnity.devnity.domain.introduction.entity.QIntroduction.introduction;

import com.devnity.devnity.domain.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionStatus;
import com.devnity.devnity.domain.introduction.entity.QIntroduction;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@RequiredArgsConstructor
public class IntroductionCustomRepositoryImpl implements
  IntroductionCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<Introduction> findIntroductionByIdAndUserId(Long introductionId, Long userId) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(introduction)
      .where(
        introduction.id.eq(introductionId),
        introduction.user.id.eq(userId),
        introduction.status.eq(IntroductionStatus.POSTED)
      ).fetchOne());
  }

  @Override
  public List<Introduction> findAllBy(SearchIntroductionRequest searchRequest, Long introductionId,
    Integer size) {
    return jpaQueryFactory
        .selectFrom(introduction)
        .where(
            ltIntroductionId(introductionId),
            introduction.user.name.like(searchRequest.getName() + "%"),
            eqCourse(searchRequest.getCourse()),
            eqGeneration(searchRequest.getGeneration()),
            eqUserRole(searchRequest.getRole()))
        .orderBy(introduction.id.desc())
        .limit(size)
        .fetch();
  }

  private BooleanExpression eqUserRole(UserRole role) {
    if (Objects.isNull(role)) {
      return null;
    }
    return introduction.user.role.eq(role);
  }

  private BooleanExpression eqGeneration(Integer generation) {
    if (Objects.isNull(generation)) {
      return null;
    }
    return introduction.user.generation.sequence.eq(generation);
  }

  private BooleanExpression eqCourse(String course) {
    if (Objects.isNull(course)) {
      return null;
    }
    return introduction.user.course.name.eq(course);
  }

  private BooleanExpression ltIntroductionId(Long introductionId) {
    if (Objects.isNull(introductionId)) {
      return null;
    }

    return introduction.id.lt(introductionId);
  }
}
