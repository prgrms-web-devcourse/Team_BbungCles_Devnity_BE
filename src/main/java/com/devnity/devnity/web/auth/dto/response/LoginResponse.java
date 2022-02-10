package com.devnity.devnity.web.auth.dto.response;

import com.devnity.devnity.domain.user.entity.Authority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {

  private final String token;

  private final Authority authority;
}
