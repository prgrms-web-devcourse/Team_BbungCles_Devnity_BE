package com.devnity.devnity.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.config.TestConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @Autowired private CourseRepository courseRepository;

  @Autowired private GenerationRepository generationRepository;

  private User user;
  private Course course;
  private Generation generation;

  @BeforeEach
  void init() {
    course = new Course("FE");
    generation = new Generation(1);
    user =
        User.builder()
            .course(course)
            .password("password")
            .name("name")
            .generation(generation)
            .role(UserRole.STUDENT)
            .email("user@gmail.com")
            .build();
  }

  @DisplayName("이메일로 사용자를 찾을 수 있다")
  @Test
  public void testFindByEmail() throws Exception {
    // given
    courseRepository.save(course);
    generationRepository.save(generation);
    userRepository.save(user);
    String email = user.getEmail();

    // when
    Optional<User> actual = userRepository.findUserByEmail(email);

    // then
    assertThat(actual).isPresent();
    assertThat(actual.get().getEmail()).isEqualTo(email);
  }

  @DisplayName("이메일 중복을 확인할 수 있다")
  @Test
  public void testExistsByEmail() throws Exception {
    // given
    courseRepository.save(course);
    generationRepository.save(generation);
    userRepository.save(user);
    String email = user.getEmail();

    // when
    boolean result = userRepository.existsByEmail(email);

    // then
    assertThat(result).isTrue();
  }
  
  @DisplayName("코스와 기수가 같은 사용자를 가져올 수 있다")
  @Test 
  public void testFindAllByCourseAndGenerationLimit() throws Exception {
    //given
    List<Course> courses = courseRepository.saveAll(List.of(course, new Course("BE")));
    List<Generation> generations = generationRepository.saveAll(
    List.of(generation, new Generation(2)));
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 40; i++) {
      users.add(
          User.builder()
              .course(courses.get(i % 2))
              .password("password" + i)
              .name("name" + i)
              .generation(generations.get(i % 2))
              .role(UserRole.STUDENT)
              .email(i + "user@gmail.com")
              .build());
    }

    userRepository.saveAll(users);

    // when
    List<User> results = userRepository.findAllByCourseAndGenerationLimit(users.get(0), 10);
    // then
    assertThat(results).hasSize(10);
    assertThat(results.get(0).getCourse()).isEqualTo(courses.get(0));
    assertThat(results.get(0).getGeneration()).isEqualTo(generations.get(0));
    assertThat(results.contains(users.get(0))).isFalse();
  }
}