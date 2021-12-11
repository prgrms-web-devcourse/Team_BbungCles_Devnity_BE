package com.devnity.devnity.test.provider;

import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
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
  private final GatherRepository gatherRepository;
  private final GatherCommentRepository gatherCommentRepository;
  private final GatherApplicantRepository gatherApplicantRepository;

  public TestHelper(
    CourseRepository courseRepository,
    GenerationRepository generationRepository,
    UserRepository userRepository, GatherRepository gatherRepository,
    GatherCommentRepository gatherCommentRepository,
    GatherApplicantRepository gatherApplicantRepository
  ) {
    this.courseRepository = courseRepository;
    this.generationRepository = generationRepository;
    this.userRepository = userRepository;
    this.gatherRepository = gatherRepository;
    this.gatherCommentRepository = gatherCommentRepository;
    this.gatherApplicantRepository = gatherApplicantRepository;
  }

  public void testStart() {
    generationRepository.save(new Generation(1));
    generationRepository.save(new Generation(2));
    courseRepository.save(new Course("FE"));
    courseRepository.save(new Course("BE"));
  }

  // 삭제 순서에 유의!! (가장 바깥 관계 테이블부터 삭제)
  public void tearDown() {
    gatherCommentRepository.deleteAll();
    gatherApplicantRepository.deleteAll();
    gatherRepository.deleteAll();
    userRepository.deleteAll();

  }

  public void testEnd(){
    gatherCommentRepository.deleteAll();
    gatherApplicantRepository.deleteAll();
    gatherRepository.deleteAll();

    courseRepository.deleteAll();
    generationRepository.deleteAll();
    userRepository.deleteAll();
  }

}
