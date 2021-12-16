package com.devnity.devnity.common.config.security.resolver;

import com.devnity.devnity.common.config.security.annotation.UserId;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.common.config.security.jwt.JwtAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
public class UserIdResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    if (parameter.getParameterAnnotation(UserId.class) == null)
        return false;
    return parameter.getParameterType().equals(Long.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
    NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    log.info("UserIdResolver.resolveArgument()");

    if (authentication == null) {
      throw new InvalidValueException("토큰이 없습니다");
    }

    Object principal = authentication.getPrincipal();

    if (principal == null) {
      throw new InvalidValueException("잘못된 토큰입니다");
    }

    JwtAuthentication jwtAuthentication = (JwtAuthentication) principal;

    if (jwtAuthentication.getUserId() == null) {
      throw new InvalidValueException("잘못된 토큰입니다");
    }

    return jwtAuthentication.getUserId();
  }
}
