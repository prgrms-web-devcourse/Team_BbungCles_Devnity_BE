package com.devnity.devnity.domain.mapgakco.repository;

import static com.devnity.devnity.domain.mapgakco.entity.QMapgakco.mapgakco;
import static com.devnity.devnity.domain.mapgakco.entity.QMapgakcoApplicant.mapgakcoApplicant;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.entity.QMapgakco;
import com.devnity.devnity.domain.mapgakco.entity.QMapgakcoApplicant;
import com.devnity.devnity.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapgakcoCustomRepositoryImpl implements
  MapgakcoCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Mapgakco> findMapgakcosHostedBy(User user) {
    return jpaQueryFactory
      .selectFrom(mapgakco)
      .where(
        mapgakco.user.eq(user),
        mapgakco.status.ne(MapgakcoStatus.DELETED)
      )
      .fetch();
  }

  @Override
  public List<Mapgakco> findMapgakcosAppliedBy(User user) {
    return jpaQueryFactory
        .selectFrom(mapgakcoApplicant)
        .join(mapgakcoApplicant.mapgakco, mapgakco)
        .where(
          mapgakcoApplicant.user.eq(user),
          mapgakco.status.ne(MapgakcoStatus.DELETED)
        )
        .orderBy(mapgakco.id.desc())
        .fetch().stream()
        .map(MapgakcoApplicant::getMapgakco)
        .collect(Collectors.toList());
  }
}
