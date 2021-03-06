package com.devnity.devnity.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserTest {

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @DisplayName("패스워드가 다르면 예외가 발생한다")
  @Test
  public void checkPasswordException() throws Exception {
    //given
    String password = "password123";

    User user =
        User.builder()
            .course(new Course("백엔드"))
            .email("admin@naver.com")
            .generation(new Generation(1))
            .password(passwordEncoder.encode(password))
            .role(UserRole.STUDENT)
            .build();
    // when

    // then
    Assertions.assertThatThrownBy(() -> user.checkPassword(passwordEncoder, "wrong password"))
        .isInstanceOf(BadCredentialsException.class);
  }


}