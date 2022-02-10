package com.devnity.devnity.domain.introduction.respository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.web.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionStatus;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import({TestConfig.class, UserProvider.class})
@DataJpaTest
class IntroductionRepositoryTest {

  @Autowired private UserRepository userRepository;
  @Autowired private IntroductionRepository introductionRepository;
  @Autowired private CourseRepository courseRepository;
  @Autowired private GenerationRepository generationRepository;
  @Autowired private UserProvider userProvider;

  @DisplayName("사용자가 저장되면 자기소개도 저장된다.")
  @Test
  public void testDefaultIntroduction() throws Exception {
    // given
    Course course = courseRepository.save(new Course("FE"));
    Generation generation = generationRepository.save(new Generation(1));

    User user = User.builder()
        .course(course)
        .generation(generation)
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
  
  @DisplayName("findIntroductionByIdAndUserId() 테스트")
  @Test 
  public void testFindIntroductionByIdAndUserId() throws Exception {
    // given
    User user = userProvider.createUser();

    // when
    Optional<Introduction> result = introductionRepository.findIntroductionByIdAndUserId(
      user.getIntroduction().getId(), user.getId());

    // then
    assertThat(result).isPresent();
    assertThat(result.get().getUser()).isEqualTo(user);
  }
  
  @DisplayName("findAllBy() 이름 검색 테스트")
  @Test 
  public void testFindAllByName() throws Exception {
    // given
    List<User> users = new ArrayList<>();
    User user = userProvider.createUser();
    int size = 5;
    users.add(user);
    for (int i = 0; i < size; i++) {
      users.add(
          User.builder()
              .email(i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.STUDENT)
              .course(user.getCourse())
              .name(user.getName() + i)
              .generation(user.getGeneration())
              .build());
    }

    userRepository.saveAll(users);
    // when
    List<Introduction> results = introductionRepository.findAllBy(
      SearchIntroductionRequest.builder().name(user.getName()).build(), users.get(users.size() - 1).getId(), size);

    // then
    assertThat(results).hasSize(size);
    assertThat(results)
        .filteredOnAssertions(
            (intro) -> assertThat(intro.getUser().getName().contains(user.getName())).isTrue());
    
  }

  @DisplayName("findAllBy() 이름, 코스 테스트")
  @Test
  public void testFindAllByNameAndCourse() throws Exception {
    // given
    List<User> users = new ArrayList<>();
    Course course = courseRepository.save(new Course("HELLO"));
    User user = userProvider.createUser();
    int size = 5;
    users.add(user);
    for (int i = 0; i < size; i++) {
      users.add(userRepository.save(
          User.builder()
              .email(i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.STUDENT)
              .course(user.getCourse())
              .name(user.getName() + i)
              .generation(user.getGeneration())
              .build()));
    }

    for (int i = 0; i < size; i++) {
      users.add(userRepository.save(
          User.builder()
              .email(size + i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.STUDENT)
              .course(course)
              .name(user.getName() + i)
              .generation(user.getGeneration())
              .build()));
    }
    // when
    SearchIntroductionRequest request =
        SearchIntroductionRequest.builder()
            .name(user.getName())
            .course(user.getCourse().getName())
            .build();

    List<Introduction> results = introductionRepository.findAllBy(
      request, users.get(users.size() - 1).getId(), size);

    // then
    assertThat(results).hasSize(size);
    assertThat(results)
        .filteredOnAssertions(
            (intro) -> assertThat(intro.getUser().getName().contains(user.getName())).isTrue());

  }

  @DisplayName("findAllBy() 이름, 코스, 기수 테스트")
  @Test
  public void testFindAllByNameAndCourseAndGeneration() throws Exception {
    // given
    List<User> users = new ArrayList<>();
    Course course = courseRepository.save(new Course("HELLO"));
    Generation generation = generationRepository.save(new Generation(1000));
    User user = userProvider.createUser();
    int size = 5;
    users.add(user);
    for (int i = 0; i < size; i++) {
      users.add(userRepository.save(
          User.builder()
              .email(i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.STUDENT)
              .course(user.getCourse())
              .name(user.getName() + i)
              .generation(user.getGeneration())
              .build()));
    }

    for (int i = 0; i < size; i++) {
      users.add(userRepository.save(
          User.builder()
              .email(size + i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.STUDENT)
              .course(course)
              .name(user.getName() + i)
              .generation(generation)
              .build()));
    }
    // when
    SearchIntroductionRequest request = SearchIntroductionRequest.builder()
      .name(user.getName())
      .course(user.getCourse().getName())
      .generation(user.getGeneration().getSequence())
      .build();

    List<Introduction> results = introductionRepository.findAllBy(
      request, users.get(users.size() - 1).getId(), size);

    // then
    assertThat(results).hasSize(size);
    assertThat(results)
        .filteredOnAssertions(
            (intro) -> assertThat(intro.getUser().getName().contains(user.getName())).isTrue());

  }

  @DisplayName("findAllBy() 이름, 코스, 기수, 역할 테스트")
  @Test
  public void testFindAllByNameAndCourseAndGenerationAndRole() throws Exception {
    // given
    List<User> users = new ArrayList<>();
    Course course = courseRepository.save(new Course("HELLO"));
    Generation generation = generationRepository.save(new Generation(1000));
    User user = userProvider.createUser();
    int size = 5;
    users.add(user);
    for (int i = 0; i < size; i++) {
      users.add(userRepository.save(
          User.builder()
              .email(i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.STUDENT)
              .course(user.getCourse())
              .name(user.getName() + i)
              .generation(user.getGeneration())
              .build()));
    }

    for (int i = 0; i < size; i++) {
      users.add(userRepository.save(
          User.builder()
              .email(size + i + "email@gmail.com")
              .password(i + "password1")
              .role(UserRole.MANAGER)
              .course(course)
              .name(user.getName() + i)
              .generation(generation)
              .build()));
    }
    // when
    SearchIntroductionRequest request =
        SearchIntroductionRequest.builder()
            .name(user.getName())
            .course(user.getCourse().getName())
            .generation(user.getGeneration().getSequence())
            .role(UserRole.MANAGER)
            .build();

    List<Introduction> results = introductionRepository.findAllBy(
      request, users.get(users.size() - 1).getId(), size);

    // then
    assertThat(results).hasSize(0);
  }
}