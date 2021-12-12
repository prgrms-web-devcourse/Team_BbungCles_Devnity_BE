package com.devnity.devnity.domain.gather.exception;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;

public class GatherApplicantNotFoundException extends BusinessException {
  public GatherApplicantNotFoundException(){
    super(ErrorCode.GATHER_APPLICANT_NOT_FOUND);
  }
}
