package com.devnity.devnity.setting.provider;

import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionLikeRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment.MapgakcoCommentRepository;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Component
public class TestHelper {

  private final InvitationRepository invitationRepository;

  private final CourseRepository courseRepository;
  private final GenerationRepository generationRepository;
  private final UserRepository userRepository;

  private final IntroductionRepository introductionRepository;
  private final IntroductionCommentRepository introductionCommentRepository;
  private final IntroductionLikeRepository introductionLikeRepository;

  private final MapgakcoRepository mapgakcoRepository;
  private final MapgakcoCommentRepository mapgakcoCommentRepository;
  private final MapgakcoApplicantRepository mapgakcoApplicantRepository;

  private final GatherRepository gatherRepository;
  private final GatherCommentRepository gatherCommentRepository;
  private final GatherApplicantRepository gatherApplicantRepository;


  // 삭제 순서에 유의!! (가장 바깥 관계 테이블부터 삭제)
  public void clean() {
    invitationRepository.deleteAll();

    mapgakcoCommentRepository.deleteAll();
    mapgakcoApplicantRepository.deleteAll();
    mapgakcoRepository.deleteAll();

    gatherCommentRepository.deleteAll();
    gatherApplicantRepository.deleteAll();
    gatherRepository.deleteAll();

    introductionCommentRepository.deleteAll();
    introductionLikeRepository.deleteAll();
    introductionRepository.deleteAll();

    userRepository.deleteAll();
    courseRepository.deleteAll();
    generationRepository.deleteAll();
  }

}
