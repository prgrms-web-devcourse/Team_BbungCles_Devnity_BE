package com.devnity.devnity.common.error.exception;

public class S3UploadException extends BusinessException  {
  public S3UploadException() {
    super(ErrorCode.S3_UPLOAD_FAILED);
  }
}
