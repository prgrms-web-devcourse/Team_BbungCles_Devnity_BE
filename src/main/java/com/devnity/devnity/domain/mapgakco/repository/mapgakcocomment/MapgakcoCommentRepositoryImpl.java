package com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment;

import static com.devnity.devnity.domain.mapgakco.entity.QMapgakcoComment.mapgakcoComment;
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
//      .join(user.generation, generation).fetchJoin() // Todo : fetch join
//      .join(user.course, course).fetchJoin()
      .where(mapgakcoEq(mapgakco), mapgakcoComment.parent.isNull())
      .orderBy(mapgakcoComment.id.desc())
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
//      .join(user.generation, generation).fetchJoin() // Todo : fetch join
//      .join(user.course, course).fetchJoin()
      .where(parentEq(parent))
      .orderBy(mapgakcoComment.id.asc())
      .fetch();
  }

  private Predicate parentEq(MapgakcoComment parent) {
    return parent != null ? mapgakcoComment.parent.eq(parent) : null;
  }

}
