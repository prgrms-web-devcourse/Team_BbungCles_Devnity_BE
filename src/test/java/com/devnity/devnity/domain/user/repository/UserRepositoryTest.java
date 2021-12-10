package com.devnity.devnity.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.user.entity.Authority;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.test.config.TestConfig;
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
            .authority(Authority.USER)
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
}