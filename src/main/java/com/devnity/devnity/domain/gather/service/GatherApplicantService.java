package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.exception.GatherApplicantNotFoundException;
import com.devnity.devnity.domain.gather.exception.InvalidGatherApplyException;
import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherApplicantService {

  private final UserRepository userRepository;
  private final GatherRetrieveService gatherServiceUtils;

  private final GatherApplicantRepository applicantRepository;

  @Transactional
  public String apply(Long userId, Long gatherId) {
    User user = UserServiceUtils.findUser(userRepository, userId);
    Gather gather = gatherServiceUtils.getGather(gatherId);

    // 1. 자신의 게시물에 신청
    // 2. 이미 신청되어 있는데 다시 신청
    // 3. 모집 상태가 아닌 게시물에 신청
    if (userId.equals(gather.getUser().getId()) &&
      applicantRepository.existsByUserAndGather(user, gather) &&
      !gather.getStatus().equals(GatherStatus.GATHERING)
    ) {
      throw new InvalidGatherApplyException();
    }

    // 신청 저장 -> 모집 게시글 상태 변경
    applicantRepository.save(GatherApplicant.of(user, gather));
    applicantRepository.flush();

    if (applicantRepository.countByGather(gather) >= gather.getApplicantLimit()){
      gather.updateStatus(GatherStatus.FULL);
    }

    return "apply success";
  }

  @Transactional
  public String cancel(Long userId, Long gatherId) {
    User user = UserServiceUtils.findUser(userRepository, userId);
    Gather gather = gatherServiceUtils.getGather(gatherId);

    // 해당 신청을 찾을 수 없다면 에러
    GatherApplicant applicant = applicantRepository.findByUserAndGather(user, gather)
      .orElseThrow(() -> new GatherApplicantNotFoundException());

    // 신청 삭제 -> 모집 게시글 상태 변경
    applicantRepository.delete(applicant);
    applicantRepository.flush();
    if(applicantRepository.countByGather(gather) < gather.getApplicantLimit()){
      gather.updateStatus(GatherStatus.GATHERING);
    }
    
    return "cancel success";
  }

}
