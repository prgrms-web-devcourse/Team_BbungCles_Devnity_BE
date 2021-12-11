package com.devnity.devnity.domain.user.utils.annotation;

import com.devnity.devnity.domain.user.utils.validator.PasswordValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
  
  String message() default "비밀번호 형식에 맞지 않습니다";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  int min() default 8;
  int max() default 20;
}
