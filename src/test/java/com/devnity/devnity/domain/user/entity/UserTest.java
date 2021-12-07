package com.devnity.devnity.domain.user.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.common.config.WebSecurityConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

class UserTest {

  private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @DisplayName("패스워드가 다르면 예외가 발생한다")
  @Test
  public void checkPasswordException() throws Exception {
    //given
    String password = "password123";

    User user = User.builder()
        .course(new Course("백엔드"))
        .email("admin@naver.com")
        .generation(new Generation(1))
        .group(new Group("USER_GROUP"))
        .password(passwordEncoder.encode(password))
        .build();
    // when

    // then
    Assertions.assertThatThrownBy(() -> user.checkPassword(passwordEncoder, "wrong password"))
        .isInstanceOf(IllegalArgumentException.class);
  }


}