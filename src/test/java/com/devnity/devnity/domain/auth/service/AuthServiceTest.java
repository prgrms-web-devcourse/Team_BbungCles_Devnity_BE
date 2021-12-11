package com.devnity.devnity.domain.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.devnity.devnity.domain.auth.dto.request.LoginRequest;
import com.devnity.devnity.domain.auth.dto.response.LoginResponse;
import com.devnity.devnity.common.config.security.jwt.JwtAuthentication;
import com.devnity.devnity.common.config.security.jwt.JwtAuthenticationToken;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @InjectMocks private AuthService authService;

  @Mock private UserRepository userRepository;

  @Mock private AuthenticationManager authenticationManager;

  @DisplayName("로그인 할 수 있다")
  @Test 
  public void testLogin() throws Exception {
    //given
    User user = User.builder()
      .course(new Course("백엔드"))
      .email("admin@naver.com")
      .generation(new Generation(1))
      .password("password")
      .role(UserRole.MANAGER)
      .name("seunghun")
      .build();

    LoginRequest request = new LoginRequest(user.getEmail(), user.getPassword());
    JwtAuthenticationToken authentication =
        new JwtAuthenticationToken(
            new JwtAuthentication("token", 1L, user.getEmail()),
            user.getPassword(),
            new SimpleGrantedAuthority(user.getAuthority().getRole()));
    authentication.setDetails(user);

    given(authenticationManager.authenticate(any())).willReturn(authentication);

    // when
    LoginResponse response = authService.login(request);

    // then
    assertThat(response.getToken()).isEqualTo("token");
  }
}