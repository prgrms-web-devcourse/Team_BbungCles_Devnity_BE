package com.devnity.devnity.domain.user.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.user.entity.UserRole;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SignUpRequestTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validatorFromFactory;

  @Autowired
  private Validator validatorInjected;

  @BeforeAll
  public static void init() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validatorFromFactory = validatorFactory.getValidator();
  }

  @AfterAll
  public static void close() {
    validatorFactory.close();
  }
  
  @DisplayName("이메일은 Null이 될 수 없다")
  @Test 
  public void testEmailNotNull() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email(null)
      .password("password123!")
      .generation(1)
      .role(UserRole.STUDENT)
      .name("함승훈")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  } 
  
  @DisplayName("이메일 형식에 맞아야 한다")
  @Test 
  public void testEmailWrongFormat() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email("abc123")
      .password("password123!")
      .generation(1)
      .role(UserRole.STUDENT)
      .name("함승훈")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  } 
  
  
  @DisplayName("비밀번호는 Null이 될 수 없다")
  @Test 
  public void testPasswordNotNull() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email("abc123@gmail.com")
      .generation(1)
      .role(UserRole.STUDENT)
      .name("함승훈")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("비밀번호는 숫자, 대문자, 소문자, 특수문자가 최소 1개씩 포함된다.")
  @Test
  public void testPasswordFormat() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email("abc123@gmail.com")
      .generation(1)
      .password("abcABC123!@#")
      .role(UserRole.STUDENT)
      .name("함승훈")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isEmpty();
  }

  @DisplayName("비밀번호는 공백이 허용되지 않는다")
  @Test
  public void testPasswordSpace() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email("abc123@gmail.com")
      .generation(1)
      .password("abcA  BC123!@#")
      .role(UserRole.STUDENT)
      .name("name")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("이름은 한글만 허용된다")
  @Test
  public void testNameNotKorean() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email("abc123@gmail.com")
      .generation(1)
      .password("abcABC123!@#")
      .role(UserRole.STUDENT)
      .name("name")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("이름은 한글만 허용된다")
  @Test
  public void testNameKorean() throws Exception {
    // given
    SignUpRequest request = SignUpRequest.builder()
      .email("abc123@gmail.com")
      .generation(1)
      .password("abcABC123!@#")
      .role(UserRole.STUDENT)
      .name("함승훈")
      .course("FE")
      .build();
    // when
    Set<ConstraintViolation<SignUpRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isEmpty();
  }
}