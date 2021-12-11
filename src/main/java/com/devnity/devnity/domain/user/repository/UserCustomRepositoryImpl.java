package com.devnity.devnity.domain.user.repository;

import static com.devnity.devnity.domain.user.entity.QUser.user;

import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserStatus;
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
        .where(
          user.course.eq(inputUser.getCourse()),
          user.generation.eq(inputUser.getGeneration()),
          user.ne(inputUser),
          user.status.eq(UserStatus.JOIN)
        )
        .orderBy(user.id.desc())
        .limit(size)
        .fetch();
  }
}