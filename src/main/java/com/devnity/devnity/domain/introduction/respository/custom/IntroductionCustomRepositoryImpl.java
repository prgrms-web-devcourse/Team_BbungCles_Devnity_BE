package com.devnity.devnity.domain.introduction.respository.custom;

import static com.devnity.devnity.domain.introduction.entity.QIntroduction.introduction;
import static com.devnity.devnity.domain.user.entity.QCourse.course;
import static com.devnity.devnity.domain.user.entity.QGeneration.generation;
import static com.devnity.devnity.domain.user.entity.QUser.user;

import com.devnity.devnity.domain.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionStatus;
import com.devnity.devnity.domain.introduction.entity.QIntroduction;
import com.devnity.devnity.domain.user.entity.QCourse;
import com.devnity.devnity.domain.user.entity.QGeneration;
import com.devnity.devnity.domain.user.entity.QUser;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

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
        .join(introduction.user, user)
        .join(user.course, course)
        .join(user.generation, generation)
        .where(
            ltIntroductionId(introductionId),
            likeName(searchRequest.getName()),
            eqCourse(searchRequest.getCourse()),
            eqGeneration(searchRequest.getGeneration()),
            eqUserRole(searchRequest.getRole()))
        .orderBy(introduction.id.desc())
        .limit(size)
        .fetch();
  }

  private BooleanExpression likeName(String name) {
    if (name == null)
      return null;

    return introduction.user.name.like(name + "%");
  }

  private BooleanExpression eqUserRole(UserRole role) {
    if (role == null) {
      return null;
    }
    return user.role.eq(role);
  }

  private BooleanExpression eqGeneration(Integer sequence) {
    if (sequence == null) {
      return null;
    }
    return generation.sequence.eq(sequence);
  }

  private BooleanExpression eqCourse(String name) {
    if (name == null) {
      return null;
    }
    return course.name.eq(name);
  }

  private BooleanExpression ltIntroductionId(Long introductionId) {
    if (introductionId == null) {
      return null;
    }
    return introduction.id.lt(introductionId);
  }
}