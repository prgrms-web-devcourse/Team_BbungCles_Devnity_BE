package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.EntityProvider;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Group;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.GroupRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//@Sql(classpath:data.sql)
//@SpringBootTest
@ExtendWith(SpringExtension.class)  // test app-context를 junit에 포함시킴
@DataJpaTest  // Jpa 관련 설정만 불러옴
class GatherRepositoryTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  CourseRepository courseRepository;
  @Autowired
  GenerationRepository generationRepository;
  @Autowired
  GroupRepository groupRepository;

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
    User user = userRepository.findById(3L).get();

    Gather gather = gatherRepository.save(EntityProvider.createGather(user));
    testEntityManager.persist(gather);  // PC에 영속화 -> DB에 실제 쿼리 날림 (?)
    testEntityManager.clear();

    GatherComment parentComment = commentRepository.save(EntityProvider.createGatherComment(gather, user));
    testEntityManager.persist(parentComment);
    testEntityManager.clear();

    GatherComment childComment = commentRepository.save(EntityProvider.createGatherCommentChild(gather, parentComment, user));
    testEntityManager.persist(childComment);
    testEntityManager.clear();

    GatherApplicant applicant = applicantRepository.save(EntityProvider.createGatherApplicant(gather, user));
    testEntityManager.persist(applicant);
    testEntityManager.clear();

    // ------------------------------------------------

    Gather resultGather = gatherRepository.findById(gather.getId()).get();  // lazy 이므로 gather 안에는 다 proxy 객체임

    // FIXME : 로그로 변경
    List<GatherComment> comments = resultGather.getComments();
    List<GatherApplicant> applicants = resultGather.getApplicants();
    System.out.println(comments);
    System.out.println(applicants);

    System.out.println(comments.get(0).getId());
    System.out.println(applicants.get(0).getId());
  }


  @Disabled
  @Test
  public void 양방향매핑_테스트_SpringBootTest() {
    Course course = courseRepository.save(new Course("BE"));
    Generation generation = generationRepository.save(new Generation(1));
    Group group = groupRepository.save(new Group("USER_GROUP"));

    User temp = User.builder()
      .group(group)
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

    // FIXME : 로그로 변경
    System.out.println(resultGather.getComments());
    System.out.println(resultGather.getApplicants());
  }

}