package com.devnity.devnity.web.error.exception;

public class S3UploadException extends BusinessException  {
  public S3UploadException() {
    super(ErrorCode.S3_UPLOAD_FAILED);
  }

  public S3UploadException(ErrorCode errorCode) {
    super(errorCode);
  }
}
