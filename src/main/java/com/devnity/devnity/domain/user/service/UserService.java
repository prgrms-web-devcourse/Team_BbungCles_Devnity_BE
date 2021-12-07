package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Group;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.GroupRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;

  private final CourseRepository courseRepository;

  private final GenerationRepository generationRepository;

  private final GroupRepository groupRepository;

  private final PasswordEncoder passwordEncoder;

  public UserInfoResponse getUserInfoBy(Long userId) {
    UserDto userDto = retrieveUser(userId);
    return new UserInfoResponse(userDto);
  }

  private UserDto retrieveUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("There is no user for id = %d", userId)));

    // TODO: Prifile Image 추가
   return UserDto.builder()
        .userId(user.getId())
        .course(user.getCourse())
        .generation(user.getGeneration())
        .name(user.getName())
        .role(user.getRole().toString())
        .build();
  }

  @Transactional
  public Long signUp(SignUpRequest request) {

    checkDuplicatedEmail(request.getEmail());

    Course course = UserServiceUtils.findCourse(courseRepository, request.getCourse());

    Generation generation = UserServiceUtils.findGeneration(generationRepository,
        request.getGeneration());

    Group group = UserServiceUtils.findGroup(
        groupRepository, request.getRole());

    User user = request.toEntity(passwordEncoder, group, course, generation);

    userRepository.save(user);

    return user.getId();
  }

  private void checkDuplicatedEmail(String email) {
    if (existsByEmail(email)) {
      throw new IllegalArgumentException(String.format("Email is duplicated. email=%s", email));
    }
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }
}
