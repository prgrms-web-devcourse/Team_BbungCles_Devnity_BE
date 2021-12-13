package com.devnity.devnity.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.entity.Authority;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks UserService userService;

  @Mock UserRepository userRepository;

  @Mock GenerationRepository generationRepository;

  @Mock CourseRepository courseRepository;

  @Mock PasswordEncoder passwordEncoder;

  @DisplayName("회원가입 할 수 있다")
  @Test 
  public void testSignUp() throws Exception {
    // given
    Generation generation = new Generation(1);
    Course course = new Course("FE");

    SignUpRequest request = SignUpRequest.builder()
        .course(course.getName())
        .email("email123@gmail.com")
        .name("seunghun")
        .password("password")
        .role(UserRole.STUDENT)
        .generation(generation.getSequence())
        .build();

    given(courseRepository.findByName(any())).willReturn(Optional.of(course));
    given(generationRepository.findBySequence(anyInt())).willReturn(Optional.of(generation));
    given(passwordEncoder.encode(any())).willReturn(request.getPassword());

    // when
    Long userId = userService.signUp(request);

    // then
    verify(userRepository).save(any());
  }

  @DisplayName("중복된 이메일은 회원가입할 수 없다")
  @Test
  public void testSignUpDuplicatedEmail() throws Exception {
    // given
    Generation generation = new Generation(1);
    Course course = new Course("FE");

    SignUpRequest request = SignUpRequest.builder()
        .course(course.getName())
        .email("user@gmail.com")
        .name("seunghun")
        .password("password")
        .role(UserRole.STUDENT)
        .generation(generation.getSequence())
        .build();

    given(userService.existsByEmail(any())).willReturn(true);

    // when
    assertThatThrownBy(() -> userService.signUp(request))
        .isInstanceOf(InvalidValueException.class);

    // then
  }
}