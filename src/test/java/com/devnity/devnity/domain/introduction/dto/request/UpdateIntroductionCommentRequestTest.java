package com.devnity.devnity.domain.introduction.dto.request;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import com.devnity.devnity.web.introduction.dto.request.UpdateIntroductionCommentRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UpdateIntroductionCommentRequestTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validatorFromFactory;

  @Autowired
  private Validator validatorInjected;

  @BeforeAll
  public static void init() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validatorFromFactory = validatorFactory.getValidator();
  }

  @DisplayName("댓글 내용은 공백이 될 수 없다.")
  @Test 
  public void testContentNotBlank() throws Exception {
    // given
    UpdateIntroductionCommentRequest request = new UpdateIntroductionCommentRequest(
      "");
    // when
    Set<ConstraintViolation<UpdateIntroductionCommentRequest>> violations = validatorFromFactory.validate(
      request);

    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("댓글 내용은 공백이 될 수 없다. 성공 테스트")
  @Test
  public void testContentNotBlankSuccess() throws Exception {
    // given
    UpdateIntroductionCommentRequest request = new UpdateIntroductionCommentRequest(
      "this is content");
    // when
    Set<ConstraintViolation<UpdateIntroductionCommentRequest>> violations = validatorFromFactory.validate(
      request);

    // then
    assertThat(violations).isEmpty();
  }
}