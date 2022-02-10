package com.devnity.devnity.domain.gather.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CreateGatherRequestTest {
  private static ValidatorFactory validatorFactory;
  private static Validator validatorFromFactory;

  @Autowired
  private Validator validatorInjected;

  @BeforeAll
  public static void init() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validatorFromFactory = validatorFactory.getValidator();
  }
  
  @DisplayName("제목은 null을 허용하지 않는다")
  @Test 
  public void testTitleNull() throws Exception {
    // given
    CreateGatherRequest request = CreateGatherRequest.builder().content("content").title(null)
      .build();

    // when
    Set<ConstraintViolation<CreateGatherRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("제목은 공백을 허용하지 않는다")
  @Test
  public void testTitleBlank() throws Exception {
    // given
    CreateGatherRequest request = CreateGatherRequest.builder().title("               ").content("content").title(null)
      .build();

    // when
    Set<ConstraintViolation<CreateGatherRequest>> violations = validatorFromFactory.validate(request);

    // then
    assertThat(violations).isNotEmpty();
  }
  
  
}