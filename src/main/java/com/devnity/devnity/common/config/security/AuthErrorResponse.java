package com.devnity.devnity.common.config.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AuthErrorResponse {
  private String message;
  private int status;


  @Builder
  private AuthErrorResponse(String message, int status) {
    this.message = message;
    this.status = status;
  }

  public static AuthErrorResponse of(AuthErrorCode errorCode) {
    return AuthErrorResponse.builder()
        .message(errorCode.getMessage())
        .status(HttpStatus.UNAUTHORIZED.value())
        .build();
  }
}
