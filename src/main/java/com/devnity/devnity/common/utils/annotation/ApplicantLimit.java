package com.devnity.devnity.common.utils.annotation;

import com.devnity.devnity.common.utils.validator.ApplicantLimitValidator;
import com.devnity.devnity.common.utils.validator.CommentValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Positive;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ApplicantLimitValidator.class)
public @interface ApplicantLimit {

  String message() default "유효하지 않은 마감 인원 입력입니다.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 1;

  int max() default 50;
}
