package com.devnity.devnity.setting.provider;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Component
public class UserProvider {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final GenerationRepository generationRepository;

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

  public User findMe(String email){
    return userRepository.findUserByEmail(email).get();
  }

}
