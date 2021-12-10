package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
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

  private final PasswordEncoder passwordEncoder;

  public UserInfoResponse getUserInfo(Long userId) {
    User user = UserServiceUtils.findUserById(userRepository, userId);
    return new UserInfoResponse(UserDto.of(user), IntroductionDto.of(user.getIntroduction()));
  }

  public SimpleUserInfoDto getSimpleUserInfo(Long userId) {
    User user = UserServiceUtils.findUserById(userRepository, userId);
    return SimpleUserInfoDto.of(user, user.getIntroduction().getProfileImgUrl());
  }

  @Transactional
  public Long signUp(SignUpRequest request) {

    checkDuplicatedEmail(request.getEmail());

    Course course = UserServiceUtils.findCourse(courseRepository, request.getCourse());

    Generation generation = UserServiceUtils.findGeneration(generationRepository,
        request.getGeneration());

    User user = request.toEntity(passwordEncoder, course, generation);

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
