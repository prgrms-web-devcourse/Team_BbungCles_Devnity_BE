package com.devnity.devnity.domain.introduction.dto.request;

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
class SaveIntroductionCommentRequestTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validatorFromFactory;

  @Autowired
  private Validator validatorInjected;

  @BeforeAll
  public static void init() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validatorFromFactory = validatorFactory.getValidator();
  }

  @DisplayName("댓글은 공백을 허용하지 않는다")
  @Test
  public void testContentBlank() throws Exception {
    // given
    SaveIntroductionCommentRequest request = SaveIntroductionCommentRequest.builder()
      .content("").parentId(1L).build();

    // when
    Set<ConstraintViolation<SaveIntroductionCommentRequest>> violations = validatorFromFactory.validate(
      request);
    // then
    assertThat(violations).isNotEmpty();
  }

  @DisplayName("부모 댓글 ID는 NULL을 허용한다")
  @Test
  public void testParentNull() throws Exception {
    // given
    SaveIntroductionCommentRequest request = SaveIntroductionCommentRequest.builder()
      .content("hello").parentId(null).build();

    // when
    Set<ConstraintViolation<SaveIntroductionCommentRequest>> violations = validatorFromFactory.validate(
      request);

    // then
    assertThat(violations).isEmpty();
  }


}