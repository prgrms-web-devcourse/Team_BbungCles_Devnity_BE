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

  @Override // Todo : user의 course, generation, Introduction fetch join
  public List<MapgakcoComment> getParentByMapgakcoWithUser(Mapgakco mapgakco) {
    return jpaQueryFactory
      .selectFrom(mapgakcoComment)
      .join(mapgakcoComment.user, user).fetchJoin()
      .where(mapgakcoEq(mapgakco), mapgakcoComment.parent.isNull())
      .fetch();
  }

  private Predicate mapgakcoEq(Mapgakco mapgakco) {
    return mapgakco != null ? mapgakcoComment.mapgakco.eq(mapgakco) : null;
  }

  @Override // Todo : user의 course, generation, Introduction fetch join
  public List<MapgakcoComment> getChildByParentWithUser(MapgakcoComment parent) {
    return jpaQueryFactory
      .selectFrom(mapgakcoComment)
      .join(mapgakcoComment.user, user).fetchJoin()
      .where(parentEq(parent))
      .fetch();
  }

  private Predicate parentEq(MapgakcoComment parent) {
    return parent != null ? mapgakcoComment.parent.eq(parent) : null;
  }

}
