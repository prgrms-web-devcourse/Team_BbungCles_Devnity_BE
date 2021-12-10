package com.devnity.devnity.common.error.exception;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

  // Common
  INVALID_INPUT_VALUE(400, "Invalid Input Value"),
  METHOD_NOT_ALLOWED(405, "Method not allowed"),
  ENTITY_NOT_FOUND(400, "Entity Not Found"),
  INTERNAL_SERVER_ERROR(500, "Server Error"),
  INVALID_TYPE_VALUE(400, "Invalid Type Value"),
  HANDLE_ACCESS_DENIED(403, "Access is Denied"),

  // S3
  S3_UPLOAD_FAILED(415, "파일 업로드에 실패하였습니다."),
  S3_NOT_SUPPORTED_EXT(415, "지원하지 않는 확장자입니다."),

  // User

  // Introduction

  // Mapgakco

  // Gather

  // jwt

  ;

  private final String message;
  private int status;

  ErrorCode(final int status, final String message) {
    this.status = status;
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public int getStatus() {
    return status;
  }


}