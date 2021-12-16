package com.devnity.devnity.common.error.exception;

import java.util.Arrays;

public enum AuthErrorCode {
  TOKEN_EXPIRED("A000", "토큰이 만료 되었습니다"),
  INVALID_TOKEN("A001", "유효하지 않는 토큰입니다"),
  BAD_CREDENTIAL("A002", "이메일 또는 비밀번호가 유효하지 않습니다"),
  EMPTY_TOKEN("A003", "토큰이 존재하지 않습니다")
  ;


  private final String code;
  private final String message;

  AuthErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public static AuthErrorCode of(String code) {

    if (code == null) return EMPTY_TOKEN;

    return Arrays.stream(AuthErrorCode.values())
        .filter(error -> error.getCode().equals(code))
        .findFirst()
        .orElseGet(() -> INVALID_TOKEN);
  }
}
