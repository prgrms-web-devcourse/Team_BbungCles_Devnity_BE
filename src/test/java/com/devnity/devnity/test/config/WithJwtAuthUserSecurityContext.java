package com.devnity.devnity.test.config;

import com.devnity.devnity.domain.auth.jwt.Jwt;
import com.devnity.devnity.domain.auth.jwt.Jwt.Claims;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.auth.jwt.JwtAuthenticationToken;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.test.annotation.WithJwtAuthUser;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithJwtAuthUserSecurityContext implements WithSecurityContextFactory<WithJwtAuthUser> {

  private final Jwt jwt;
  private final UserRepository userRepository;

  public WithJwtAuthUserSecurityContext(Jwt jwt, UserRepository userRepository) {
    this.jwt = jwt;
    this.userRepository = userRepository;
  }

  @Override
  public SecurityContext createSecurityContext(WithJwtAuthUser withJwtAuthUser) {
    String email = withJwtAuthUser.email();
    String role = withJwtAuthUser.role();
    User user = userRepository
        .findUserByEmail(email)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("There is no user for email = %s", email)));

    String jwtToken = jwt.sign(Claims.from(user.getId(), email, role));

    JwtAuthentication authentication = new JwtAuthentication(jwtToken, user.getId(), email);
    JwtAuthenticationToken jwtAuthenticationToken =
        new JwtAuthenticationToken(
            authentication,
            user.getPassword(),
            new SimpleGrantedAuthority(role));

    SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    return SecurityContextHolder.getContext();
  }

}
