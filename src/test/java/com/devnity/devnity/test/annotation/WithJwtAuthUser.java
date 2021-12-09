package com.devnity.devnity.test.annotation;

import com.devnity.devnity.test.config.WithJwtAuthUserSecurityContext;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithJwtAuthUserSecurityContext.class)
public @interface WithJwtAuthUser {

  String email();
  String role();
}
