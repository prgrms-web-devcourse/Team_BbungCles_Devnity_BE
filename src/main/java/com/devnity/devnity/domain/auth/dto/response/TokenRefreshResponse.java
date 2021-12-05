package com.devnity.devnity.domain.auth.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenRefreshResponse {

  private String token;
  private String refreshToken;

  public TokenRefreshResponse(String token, String refreshToken) {
    this.token = token;
    this.refreshToken = refreshToken;
  }
}
