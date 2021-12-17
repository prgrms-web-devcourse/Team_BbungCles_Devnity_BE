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

  // Admin
  LINK_NOT_FOUND(400, "유효기간이 만료된 링크입니다."),

  // User
  USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다"),
  EMAIL_DUPLICATE(400, "이미 존재하는 이메일입니다"),

  // Introduction
  INTRODUCTION_NOT_FOUND(404, "자기소개를 찾을 수 없습니다"),
  INTRODUCTION_LIKE_NOT_FOUND(404, "자기소개에 좋아요를 하지 않았습니다"),
  INTRODUCTION_LIKE_DUPLICATE(400, "이미 좋아요가 생성되었습니다"),
  INTRODUCTION_COMMENT_NOT_FOUND(404, "자기소개 댓글을 찾을 수 없습니다"),
  INTRODUCTION_COMMENT_ALREADY_DELETED(400, "자기소개 댓글이 이미 삭제되었습니다"),
  INTRODUCTION_COMMENT_IS_CHILD(400, "이미 상위 댓글이 존재합니다"),

  // Mapgakco
  MAPGAKCO_NOT_FOUND(404, "해당 맵각코를 찾을 수 없습니다."),
  MAPGAKCO_APPLICANT_NOT_FOUND(404, "해당 맵각코 신청자를 찾을 수 없습니다."),
  MAPGAKCO_COMMENT_NOT_FOUND(404, "해당 맵각코 댓글을 찾을 수 없습니다."),
  MAPGAKCO_NOT_GATHERING(400, "모집중인 맵각코가 아닙니다."),
  INVALID_MAPGAKCO_PARENT_COMMENT(400, "대댓글에 댓글을 달 수 없습니다."),
  INVALID_MEETINGAT(400, "현재 등록된 날짜 이전으로 변경할 수 없습니다."),

  // Gather
  GATHER_NOT_FOUND(404, "해당 모집 게시글을 찾을 수 없습니다."),
  UPDATE_GATHER_NOT_ALLOWED(403, "모집 게시글/댓글은 작성자만 수정할 수 있습니다."),
  DELETE_GATHER_NOT_ALLOWED(403, "모집 게시글/댓글은 작성자만 삭제할 수 있습니다."),
  CLOSE_GATHER_NOT_ALLOWED(403, "모집 게시글/댓글은 작성자만 삭제할 수 있습니다."),
  CANNOT_UPDATE_CLOSED_GATHER(403, "마감된 모집은 수정할 수 없습니다."),
  INVALID_DEADLINE(400, "마감 날짜는 현재 날짜 이전일 수 없습니다."),
  INVALID_APPLICANT_LIMIT(400, "마감 인원은 현재 신청자 수 미만으로 변경할 수 없습니다."),
  GATHER_COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다."),
  GATHER_APPLICANT_NOT_FOUND(404, "이미 취소된 신청입니다."),
  ALREADY_CLOSED_GATHER(400, "모집이 마감되었습니다."),
  CANNOT_APPLY_MYSELF(400, "자신의 모집 게시글엔 신청할 수 없습니다."),
  ALREADY_APPLIED(400, "이미 신청되었습니다."),

  // Auth
  BAD_CREDENTIAL(401, "이메일 또는 비밀번호가 유효하지 않습니다")
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