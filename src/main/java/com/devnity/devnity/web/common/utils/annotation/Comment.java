package com.devnity.devnity.web.common.utils.annotation;

import com.devnity.devnity.web.common.utils.validator.CommentValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CommentValidator.class)
public @interface Comment {

  String message() default "댓글이 유효하지 않습니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 1;
  int max() default 150;
}
