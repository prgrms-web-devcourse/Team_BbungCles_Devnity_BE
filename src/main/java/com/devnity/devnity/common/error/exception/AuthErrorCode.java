package com.devnity.devnity.common.error.exception;

import java.util.Arrays;
import javax.servlet.http.HttpServletResponse;

public enum AuthErrorCode {
  TOKEN_EXPIRED(HttpServletResponse.SC_UNAUTHORIZED,"A000", "토큰이 만료 되었습니다"),
  INVALID_TOKEN(HttpServletResponse.SC_UNAUTHORIZED,"A001", "유효하지 않는 토큰입니다"),
  BAD_CREDENTIAL(HttpServletResponse.SC_UNAUTHORIZED,"A002", "이메일 또는 비밀번호가 유효하지 않습니다"),
  EMPTY_TOKEN(HttpServletResponse.SC_UNAUTHORIZED,"A003", "토큰이 존재하지 않습니다"),
  ACCESS_DENIED(HttpServletResponse.SC_FORBIDDEN,"A004", "유효하지 않는 접근입니다")
  ;

  private final int status;
  private final String code;
  private final String message;

  AuthErrorCode(int status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  public static AuthErrorCode of(String code) {

    if (code == null) return EMPTY_TOKEN;

    return Arrays.stream(AuthErrorCode.values())
        .filter(error -> error.getCode().equals(code))
        .findFirst()
        .orElseGet(() -> INVALID_TOKEN);
  }
}
