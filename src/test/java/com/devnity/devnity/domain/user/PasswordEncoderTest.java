package com.devnity.devnity.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class PasswordEncoderTest {

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  void convert(){
    String encode = passwordEncoder.encode("Abcd1234!");
    log.info(encode);
  }

}
