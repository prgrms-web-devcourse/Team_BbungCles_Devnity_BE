package com.devnity.devnity.domain.user.utils.annotation;

import com.devnity.devnity.domain.user.utils.validator.EmailValidator;
import com.devnity.devnity.domain.user.utils.validator.UserNameValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
public @interface UserName {

  String message() default "이름 형식이 맞지 않습니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 2;
  int max() default 5;
}
