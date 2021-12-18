package com.devnity.devnity.domain.user.repository;

import static com.devnity.devnity.domain.introduction.entity.QIntroduction.introduction;
import static com.devnity.devnity.domain.user.entity.QCourse.course;
import static com.devnity.devnity.domain.user.entity.QGeneration.generation;
import static com.devnity.devnity.domain.user.entity.QUser.user;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserStatus;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<User> findAllByCourseAndGenerationLimit(User inputUser, int size) {
    return jpaQueryFactory
        .selectFrom(user)
        .join(user.generation, generation).fetchJoin()
        .join(user.course, course).fetchJoin()
        .join(user.introduction, introduction).fetchJoin()
      .where(
        user.course.eq(inputUser.getCourse()),
        user.generation.eq(inputUser.getGeneration()),
        user.ne(inputUser),
        user.status.eq(UserStatus.ACTIVE)
      )
      .orderBy(user.id.desc())
      .limit(size)
      .fetch();
  }

  @Override
  public List<User> getAllByCourseAndGeneration(Course courseIn, Generation generationIn) {
    return jpaQueryFactory
      .selectFrom(user)
      .join(user.generation, generation).fetchJoin()
      .join(user.course, course).fetchJoin()
      .join(user.introduction, introduction).fetchJoin()
      .where(
        courseEq(courseIn),
        generationEq(generationIn),
        user.status.eq(UserStatus.ACTIVE)
      )
      .fetch();
  }

  private Predicate courseEq(Course courseIn) {
    return courseIn != null ? user.course.eq(courseIn) : null;
  }

  private Predicate generationEq(Generation generationIn) {
    return generationIn != null ? user.generation.eq(generationIn) : null;
  }

}
