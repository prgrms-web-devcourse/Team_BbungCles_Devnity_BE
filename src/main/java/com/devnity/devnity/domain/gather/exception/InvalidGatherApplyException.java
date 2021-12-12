package com.devnity.devnity.domain.gather.exception;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;

public class InvalidGatherApplyException extends BusinessException {
  public InvalidGatherApplyException(){
    super(ErrorCode.INVALID_GATHER_APPLY);
  }
}
