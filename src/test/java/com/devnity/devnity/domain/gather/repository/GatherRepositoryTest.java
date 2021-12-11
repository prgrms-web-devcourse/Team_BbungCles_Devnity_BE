package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.EntityProvider;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.config.TestConfig;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@Sql(classpath:data.sql)
//@SpringBootTest
@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)  // test app-context를 junit에 포함시킴
@DataJpaTest  // Jpa 관련 설정만 불러옴
@Slf4j
class GatherRepositoryTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  CourseRepository courseRepository;
  @Autowired
  GenerationRepository generationRepository;

  @Autowired
  GatherRepository gatherRepository;
  @Autowired
  GatherCommentRepository commentRepository;
  @Autowired
  GatherApplicantRepository applicantRepository;

  @Autowired
  TestEntityManager testEntityManager;

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  public void 양방향매핑_테스트_DataJpaTest() {
    Generation generation = new Generation(1);
    Course course = new Course("BE");
    User user =
        User.builder()
            .email("test@mail.com")
            .name("test")
            .role(UserRole.STUDENT)
            .password("$2a$10$B32L76wyCEGqG/UVKPYk9uqZHCWb7k4ci98VTQ7l.dCEib/kzpKGe")
            .generation(generation)
            .course(course)
            .build();

    generationRepository.save(generation);
    courseRepository.save(course);
    userRepository.save(user);

    Gather gather = gatherRepository.save(EntityProvider.createGather(user));

    GatherComment parentComment = commentRepository.save(EntityProvider.createGatherComment(gather, user));

    GatherComment childComment = commentRepository.save(EntityProvider.createGatherCommentChild(gather, parentComment, user));

    GatherApplicant applicant = applicantRepository.save(EntityProvider.createGatherApplicant(gather, user));

    // 영속성 컨텍스트에서 조회되지 않도록 컨텍스트는 clear 해준다.
    testEntityManager.clear();

    // ------------------------------------------------

    Gather resultGather = gatherRepository.findById(gather.getId()).get();  // lazy 이므로 gather 안에는 다 proxy 객체임

    List<GatherComment> comments = resultGather.getComments();
    List<GatherApplicant> applicants = resultGather.getApplicants();
    log.info("{}", comments);
    log.info("{}", applicants);

    log.info("{}", comments.get(0).getId());
    log.info("{}", applicants.get(0).getId());

  }


  @Disabled
  @Test
  public void 양방향매핑_테스트_SpringBootTest() {
    Course course = courseRepository.save(new Course("BE"));
    Generation generation = generationRepository.save(new Generation(1));

    User temp = User.builder()
      .course(course)
      .generation(generation)
      .email("test@mail.com")
      .name("제발돼라")
      .role(UserRole.STUDENT)
      .password(passwordEncoder.encode("00000000"))
      .build();

    User user = userRepository.save(temp);

    Gather gather = gatherRepository.save(EntityProvider.createGather(user));

    GatherComment parentComment = commentRepository.save(EntityProvider.createGatherComment(gather, user));

    GatherComment childComment = commentRepository.save(EntityProvider.createGatherCommentChild(gather, parentComment, user));

    GatherApplicant applicant = applicantRepository.save(EntityProvider.createGatherApplicant(gather, user));

    // ------------------------------------------------

    Gather resultGather = gatherRepository.findById(gather.getId()).get();

    log.info("{}", resultGather.getComments());
    log.info("{}", resultGather.getApplicants());
  }

}