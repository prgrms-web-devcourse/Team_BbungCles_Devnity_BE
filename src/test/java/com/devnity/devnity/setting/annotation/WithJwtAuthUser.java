package com.devnity.devnity.setting.annotation;

import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.config.WithJwtAuthUserSecurityContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.security.test.context.support.WithSecurityContext;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithJwtAuthUserSecurityContext.class)
public @interface WithJwtAuthUser {

  String email();

  UserRole role() default UserRole.STUDENT;

  String password() default "password123!@#";

  String name() default "데브코스";

  String course() default "FE";

  int generation() default 1;
}
