package com.devnity.devnity.setting.provider;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class UserProvider {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final GenerationRepository generationRepository;
  private final IntroductionRepository introductionRepository;

  private final String courseName = "FE";
  private final int sequence = 1;

  @Transactional
  public User createUser(){
    Course course =
        courseRepository
            .findByName(courseName)
            .orElseGet(() -> courseRepository.save(new Course(courseName)));

    Generation generation =
        generationRepository
            .findBySequence(sequence)
            .orElseGet(() -> generationRepository.save(new Generation(sequence)));

    return userRepository.save(
      User.builder()
        .email("dummy@mail.com")
        .name("dummy1")
        .role(UserRole.STUDENT)
        .password("$2a$10$B32L76wyCEGqG/UVKPYk9uqZHCWb7k4ci98VTQ7l.dCEib/kzpKGe")
        .generation(generation)
        .course(course)
        .build()
    );
  }

  @Transactional
  public User createUser(String crs, int seq, String email) {
    Course course =
      courseRepository
        .findByName(courseName)
        .orElseGet(() -> courseRepository.save(new Course(crs)));

    Generation generation =
      generationRepository
        .findBySequence(sequence)
        .orElseGet(() -> generationRepository.save(new Generation(seq)));

    return userRepository.save(
      User.builder()
        .email(email)
        .name("dummy1")
        .role(UserRole.STUDENT)
        .password("$2a$10$B32L76wyCEGqG/UVKPYk9uqZHCWb7k4ci98VTQ7l.dCEib/kzpKGe")
        .generation(generation)
        .course(course)
        .build()
    );
  }

  @Transactional
  public User createUser(String email){
    Course course =
        courseRepository
            .findByName(courseName)
            .orElseGet(() -> courseRepository.save(new Course(courseName)));

    Generation generation =
        generationRepository
            .findBySequence(sequence)
            .orElseGet(() -> generationRepository.save(new Generation(sequence)));

    return userRepository.save(
      User.builder()
        .email(email)
        .name("dummy1")
        .role(UserRole.STUDENT)
        .password("$2a$10$B32L76wyCEGqG/UVKPYk9uqZHCWb7k4ci98VTQ7l.dCEib/kzpKGe")
        .generation(generation)
        .course(course)
        .build()
    );
  }

  @Transactional
  public User createUser(String crs, int seq, Double latitude, Double longitude, String email) {
    Course course =
      courseRepository
        .findByName(crs)
        .orElseGet(() -> courseRepository.save(new Course(crs)));

    Generation generation =
      generationRepository
        .findBySequence(seq)
        .orElseGet(() -> generationRepository.save(new Generation(seq)));

    User user = userRepository.save(
      User.builder()
        .email(email)
        .name("dummy1")
        .role(UserRole.STUDENT)
        .password("$2a$10$B32L76wyCEGqG/UVKPYk9uqZHCWb7k4ci98VTQ7l.dCEib/kzpKGe")
        .generation(generation)
        .course(course)
        .build()
    );

    Introduction introduction = user.getIntroduction();
    introduction.update(
      Introduction.builder()
        .description("description")
        .profileImgUrl("profile")
        .githubUrl("github")
        .blogUrl("blog")
        .latitude(latitude)
        .longitude(longitude)
        .mbti(Mbti.INFP)
        .summary("summary")
        .build());

    return user;
  }

  public User findMe(String email) {
    return userRepository.findUserByEmail(email).get();
  }

}
