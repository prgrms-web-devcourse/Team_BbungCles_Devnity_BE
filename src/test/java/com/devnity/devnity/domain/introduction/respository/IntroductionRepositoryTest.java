package com.devnity.devnity.domain.introduction.respository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionStatus;
import com.devnity.devnity.domain.user.entity.Authority;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class IntroductionRepositoryTest {

  @Autowired private UserRepository userRepository;
  @Autowired private IntroductionRepository introductionRepository;
  @Autowired private CourseRepository courseRepository;
  @Autowired private GenerationRepository generationRepository;

  @DisplayName("사용자가 저장되면 자기소개도 저장된다.")
  @Test
  public void testDefaultIntroduction() throws Exception {
    // given
    Course course = courseRepository.save(new Course("FE"));
    Generation generation = generationRepository.save(new Generation(1));

    User user = User.builder()
        .course(course)
        .generation(generation)
        .authority(Authority.USER)
        .name("seunghun")
        .password("password")
        .role(UserRole.STUDENT)
        .email("email@gmail.com")
        .build();

    // when
    userRepository.save(user);

    // then
    List<Introduction> list = introductionRepository.findAll();
    assertThat(list).isNotEmpty();
    assertThat(list.get(0).getStatus()).isEqualTo(IntroductionStatus.POSTED);
  }
}