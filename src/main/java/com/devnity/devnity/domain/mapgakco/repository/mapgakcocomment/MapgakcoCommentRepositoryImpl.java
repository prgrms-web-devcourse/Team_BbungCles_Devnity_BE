package com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment;

import static com.devnity.devnity.domain.mapgakco.entity.QMapgakcoComment.mapgakcoComment;
import static com.devnity.devnity.domain.user.entity.QCourse.course;
import static com.devnity.devnity.domain.user.entity.QGeneration.generation;
import static com.devnity.devnity.domain.user.entity.QUser.user;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapgakcoCommentRepositoryImpl implements MapgakcoCommentRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<MapgakcoComment> getParentByMapgakcoWithUser(Mapgakco mapgakco) {
    return jpaQueryFactory
      .selectFrom(mapgakcoComment)
      .join(mapgakcoComment.user, user).fetchJoin()
      .join(user.generation, generation).fetchJoin()
      .join(user.course, course).fetchJoin()
      .where(mapgakcoEq(mapgakco), mapgakcoComment.parent.isNull())
      .fetch();
  }

  private Predicate mapgakcoEq(Mapgakco mapgakco) {
    return mapgakco != null ? mapgakcoComment.mapgakco.eq(mapgakco) : null;
  }

  @Override
  public List<MapgakcoComment> getChildByParentWithUser(MapgakcoComment parent) {
    return jpaQueryFactory
      .selectFrom(mapgakcoComment)
      .join(mapgakcoComment.user, user).fetchJoin()
      .join(user.generation, generation).fetchJoin()
      .join(user.course, course).fetchJoin()
      .where(parentEq(parent))
      .fetch();
  }

  private Predicate parentEq(MapgakcoComment parent) {
    return parent != null ? mapgakcoComment.parent.eq(parent) : null;
  }

}
