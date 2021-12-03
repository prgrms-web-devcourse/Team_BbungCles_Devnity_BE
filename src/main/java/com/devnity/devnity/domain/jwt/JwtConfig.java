package com.devnity.devnity.domain.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

  private String header;

  private String issuer;

  private String clientSecret;

  private int expirySeconds;
}
