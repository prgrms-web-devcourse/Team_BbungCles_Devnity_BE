package com.devnity.devnity.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class LoginResponse {

  private final String token;
  private final String refreshToken;
}

