package com.devnity.devnity.domain.auth.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenRefreshRequest {

  @NotBlank
  private String refreshToken;

  public TokenRefreshRequest(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
