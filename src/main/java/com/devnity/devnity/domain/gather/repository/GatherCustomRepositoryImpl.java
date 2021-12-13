package com.devnity.devnity.domain.gather.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GatherCustomRepositoryImpl implements GatherCustomRepository {

  private final JPAQueryFactory jpaQueryFactory;


}
