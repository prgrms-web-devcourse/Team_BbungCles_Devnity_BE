package com.devnity.devnity.domain.gather.exception;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;

public class GatherNotFoundException extends BusinessException {
  public GatherNotFoundException() {
    super(ErrorCode.GATHER_NOT_FOUND);
  }

  public GatherNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
