package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherApplicantService {

  private final UserRetrieveService userRetrieveService;
  private final GatherRetrieveService gatherRetrieveService;

  private final GatherApplicantRepository applicantRepository;

  @Transactional
  public String apply(Long userId, Long gatherId) {
    User me = userRetrieveService.getUser(userId);
    Gather gather = gatherRetrieveService.getGather(gatherId);

    // 1. 자신의 게시물에 신청
    if(gather.isWrittenBy(me)){
      throw new InvalidValueException(
        String.format("자신의 게시물에 스스로 신청할 수 없음. (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.CANNOT_APPLY_MYSELF
      );
    }
    // 2. 이미 신청되어 있는데 다시 신청
    if (applicantRepository.existsByUserAndGather(me, gather)) {
      throw new InvalidValueException(
        String.format("해당 GatherApplicant가 이미 존재합니다. (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.ALREADY_APPLIED
      );
    }
    // 신청 저장 -> 모집 게시글 상태 변경
    gather.addApplicant(GatherApplicant.of(me, gather));
    return "apply success";
  }

  @Transactional
  public String cancel(Long userId, Long gatherId) {
    GatherApplicant applicant = gatherRetrieveService.getApplicant(userId, gatherId);
    applicant.cancel();
    return "cancel success";
  }

}
