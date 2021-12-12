package com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MapgakcoApplicantQueryMethodTest {

  @Autowired
  MapgakcoApplicantRepository mapgakcoApplicantRepository;
  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  CourseRepository courseRepository;
  @Autowired
  GenerationRepository generationRepository;
  @Autowired
  IntroductionRepository introductionRepository;

  private Mapgakco mapgakco;
  private User user;
  private MapgakcoApplicant applicant;

  @BeforeEach
  public void setUp() {
    Generation generation = new Generation(1);
    Course course = new Course("FE");
    User newUser = User.builder()
      .course(course)
      .generation(generation)
      .name("name")
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@gmail.com")
      .build();

    courseRepository.save(course);
    generationRepository.save(generation);
    user = userRepository.save(newUser);

    Mapgakco newMapgakco = Mapgakco.builder()
      .title("맵각코")
      .applicantLimit(2)
      .deadline(LocalDateTime.now())
      .content("모각코 모집중")
      .location("어대역 5번출구")
      .latitude(12.5)
      .longitude(12.5)
      .meetingAt(LocalDateTime.now())
      .user(user)
      .build();
    mapgakco = mapgakcoRepository.save(newMapgakco);

    MapgakcoApplicant newApplicant = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();

    applicant = mapgakcoApplicantRepository.save(newApplicant);
  }

  @AfterEach
  public void clean() {
    introductionRepository.deleteAll();
    mapgakcoApplicantRepository.deleteAll();
    mapgakcoRepository.deleteAll();
    userRepository.deleteAll();
    courseRepository.deleteAll();
    generationRepository.deleteAll();
  }

  @Test
  @DisplayName("MapgakcoApplicantRepository의 쿼리메소드들이 잘 작동한다.")
  public void queryMethodTest() {
    // given
    Optional<MapgakcoApplicant> found1 = mapgakcoApplicantRepository.findByMapgakcoAndUser(mapgakco,
      user);
    assertNotEquals(found1, Optional.empty());
    assertEquals(found1.get(), applicant);

    // when
    mapgakcoApplicantRepository.deleteByMapgakcoAndUser(mapgakco, user);

    // then
    Optional<MapgakcoApplicant> found2 = mapgakcoApplicantRepository.findByMapgakcoAndUser(mapgakco,
      user);
    assertEquals(found2, Optional.empty());

  }


}
