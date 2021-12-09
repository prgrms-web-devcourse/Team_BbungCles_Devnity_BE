package com.devnity.devnity.domain.auth.jwt;

import java.util.Collection;
import java.util.List;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private final Object principal;

  private String credentials;

  public JwtAuthenticationToken(Object principal, String credentials) {
    super(null);
    super.setAuthenticated(false);

    this.principal = principal;
    this.credentials = credentials;
  }

  public JwtAuthenticationToken(Object principal, String credentials,
      GrantedAuthority authority) {
    super(List.of(authority));
    super.setAuthenticated(true);

    this.credentials = credentials;
    this.principal = principal;
  }

  @Override
  public String getCredentials() {
    return credentials;
  }

  @Override
  public Object getPrincipal() {
    return principal;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
    if (authenticated) {
      throw new IllegalArgumentException("Cannot set this token to trusted");
    }
    super.setAuthenticated(false);
  }

  @Override
  public void eraseCredentials() {
    credentials = null;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("JwtAuthenticationToken{");
    sb.append("principal=").append(principal);
    sb.append(", credentials='").append(credentials).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
