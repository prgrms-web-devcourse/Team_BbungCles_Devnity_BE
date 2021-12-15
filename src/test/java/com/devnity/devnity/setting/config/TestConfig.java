package com.devnity.devnity.setting.config;

import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionLikeRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

// DataJpa 테스트 사용시 떠야하는 Bean 목록
@TestConfiguration
public class TestConfig {

  @Autowired
  private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public TestHelper testHelper(
    CourseRepository courseRepository,
    GenerationRepository generationRepository,
    UserRepository userRepository,
    IntroductionRepository introductionRepository,
    IntroductionCommentRepository introductionCommentRepository,
    IntroductionLikeRepository introductionLikeRepository,
    MapgakcoRepository mapgakcoRepository,
    MapgakcoCommentRepository mapgakcoCommentRepository,
    MapgakcoApplicantRepository mapgakcoApplicantRepository,
    GatherRepository gatherRepository,
    GatherCommentRepository gatherCommentRepository,
    GatherApplicantRepository gatherApplicantRepository
  ) {
    return new TestHelper(
      courseRepository,
      generationRepository,
      userRepository,
      introductionRepository,
      introductionCommentRepository,
      introductionLikeRepository,
      mapgakcoRepository,
      mapgakcoCommentRepository,
      mapgakcoApplicantRepository,
      gatherRepository,
      gatherCommentRepository,
      gatherApplicantRepository
    );
  }

  @Bean
  public GatherProvider gatherProvider(
    GatherRepository gatherRepository,
    GatherCommentRepository gatherCommentRepository,
    GatherApplicantRepository gatherApplicantRepository
  ) {
    return new GatherProvider(
      gatherRepository,
      gatherCommentRepository,
      gatherApplicantRepository
    );
  }

  @Bean
  public UserProvider userProvider(
    CourseRepository courseRepository,
    GenerationRepository generationRepository,
    UserRepository userRepository
  ) {
    return new UserProvider(
      userRepository,
      courseRepository,
      generationRepository
    );
  }
}
