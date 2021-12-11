package com.devnity.devnity.domain.gather.exception;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;

public class GatherCommentNotFoundException extends BusinessException {
  public GatherCommentNotFoundException() {
    super(ErrorCode.GATHER_COMMENT_NOT_FOUND);
  }
}
