package com.devnity.devnity.web.common.config.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

  private String header;

  private String issuer;

  private String clientSecret;

  private int expirySeconds;
}
