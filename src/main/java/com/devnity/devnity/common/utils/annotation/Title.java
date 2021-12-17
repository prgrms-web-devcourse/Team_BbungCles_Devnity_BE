package com.devnity.devnity.common.utils.annotation;

import com.devnity.devnity.common.utils.validator.TitleValidator;
import com.devnity.devnity.common.utils.validator.UserNameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleValidator.class)
public @interface Title {

  String message() default "제목 형식이 맞지 않습니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 1;
  int max() default 50;
}
