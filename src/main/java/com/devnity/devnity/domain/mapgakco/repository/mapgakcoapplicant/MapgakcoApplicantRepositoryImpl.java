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

  @Override // Todo : user의 course, generation, Introduction fetch join
  public List<MapgakcoApplicant> getByMapgakcoWithUser(Mapgakco mapgakco) {
    return jpaQueryFactory
      .selectFrom(mapgakcoApplicant)
      .join(mapgakcoApplicant.user, user).fetchJoin()
      .where(mapgakcoApplicant.mapgakco.eq(mapgakco))
      .fetch();
  }

}
