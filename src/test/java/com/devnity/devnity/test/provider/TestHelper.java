package com.devnity.devnity.test.provider;

import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class TestHelper {

  private final CourseRepository courseRepository;
  private final GenerationRepository generationRepository;
  private final UserRepository userRepository;

  private final IntroductionRepository introductionRepository;
  // introduction 코멘트, like 레포 추가해야됨.

  private final MapgakcoRepository mapgakcoRepository;
  private final MapgakcoCommentRepository mapgakcoCommentRepository;
  private final MapgakcoApplicantRepository mapgakcoApplicantRepository;

  private final GatherRepository gatherRepository;
  private final GatherCommentRepository gatherCommentRepository;
  private final GatherApplicantRepository gatherApplicantRepository;

  public TestHelper(
    CourseRepository courseRepository,
    GenerationRepository generationRepository,
    UserRepository userRepository,
    IntroductionRepository introductionRepository,
    MapgakcoRepository mapgakcoRepository,
    MapgakcoCommentRepository mapgakcoCommentRepository,
    MapgakcoApplicantRepository mapgakcoApplicantRepository, GatherRepository gatherRepository,
    GatherCommentRepository gatherCommentRepository,
    GatherApplicantRepository gatherApplicantRepository
  ) {
    this.courseRepository = courseRepository;
    this.generationRepository = generationRepository;
    this.userRepository = userRepository;
    this.introductionRepository = introductionRepository;
    this.mapgakcoRepository = mapgakcoRepository;
    this.mapgakcoCommentRepository = mapgakcoCommentRepository;
    this.mapgakcoApplicantRepository = mapgakcoApplicantRepository;
    this.gatherRepository = gatherRepository;
    this.gatherCommentRepository = gatherCommentRepository;
    this.gatherApplicantRepository = gatherApplicantRepository;
  }

  // 삭제 순서에 유의!! (가장 바깥 관계 테이블부터 삭제)
  public void clean() {
    mapgakcoCommentRepository.deleteAll();
    mapgakcoApplicantRepository.deleteAll();
    mapgakcoRepository.deleteAll();

    gatherCommentRepository.deleteAll();
    gatherApplicantRepository.deleteAll();
    gatherRepository.deleteAll();

    introductionRepository.deleteAll();

    userRepository.deleteAll();
    courseRepository.deleteAll();
    generationRepository.deleteAll();
  }

}
