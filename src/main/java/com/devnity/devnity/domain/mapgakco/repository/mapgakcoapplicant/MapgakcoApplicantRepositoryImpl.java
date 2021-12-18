package com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant;

import static com.devnity.devnity.domain.mapgakco.entity.QMapgakcoApplicant.mapgakcoApplicant;
import static com.devnity.devnity.domain.user.entity.QUser.user;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapgakcoApplicantRepositoryImpl implements MapgakcoApplicantRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<MapgakcoApplicant> getByMapgakcoWithUser(Mapgakco mapgakco) {
    return jpaQueryFactory
      .selectFrom(mapgakcoApplicant)
      .join(mapgakcoApplicant.user, user).fetchJoin()
//      .join(user.generation, generation).fetchJoin() // Todo : fetch join
//      .join(user.course, course).fetchJoin()
      .where(mapgakcoApplicant.mapgakco.eq(mapgakco))
      .fetch();
  }

}
