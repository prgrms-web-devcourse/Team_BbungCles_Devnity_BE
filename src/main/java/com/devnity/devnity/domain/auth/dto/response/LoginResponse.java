package com.devnity.devnity.domain.auth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {

  private final String token;

  private final String groupName;
}
