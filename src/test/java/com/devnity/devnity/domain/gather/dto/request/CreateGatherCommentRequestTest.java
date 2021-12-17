package com.devnity.devnity.domain.gather.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherCommentRequest.CreateGatherCommentRequestBuilder;
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
class CreateGatherCommentRequestTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validatorFromFactory;

  @Autowired
  private Validator validatorInjected;

  @BeforeAll
  public static void init() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validatorFromFactory = validatorFactory.getValidator();
  }
  
  @DisplayName("댓글은 null을 허용하지 않는다")
  @Test 
  public void testCommentNull() throws Exception {
    // given
    CreateGatherCommentRequest request = CreateGatherCommentRequest.builder().content(null).build();

    // when
    Set<ConstraintViolation<CreateGatherCommentRequest>> violations = validatorFromFactory.validate(
      request);

    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("댓글은 공백을 허용하지 않는다")
  @Test
  public void testCommentBlank() throws Exception {
    // given
    CreateGatherCommentRequest request = CreateGatherCommentRequest.builder().content("     ").build();

    // when
    Set<ConstraintViolation<CreateGatherCommentRequest>> violations = validatorFromFactory.validate(
      request);

    // then
    assertThat(violations).isNotEmpty();
  }
}