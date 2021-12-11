package com.devnity.devnity.common.config.security.jwt;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class JwtAuthentication {

  private final String token;

  private final Long userId;

  private final String email;

  public JwtAuthentication(String token, Long userId, String email) {

    Assert.notNull(token, "token must be provided");
    Assert.notNull(userId, "userId must be provided");
    Assert.notNull(email, "email must be provided");

    this.token = token;
    this.userId = userId;
    this.email = email;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("JwtAuthentication{");
    sb.append("token='").append(token).append('\'');
    sb.append(", userId=").append(userId);
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
