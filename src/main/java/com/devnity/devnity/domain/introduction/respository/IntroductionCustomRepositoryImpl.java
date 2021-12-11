package com.devnity.devnity.domain.introduction.respository;

import static com.devnity.devnity.domain.introduction.entity.QIntroduction.introduction;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionStatus;
import com.devnity.devnity.domain.introduction.entity.QIntroduction;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
