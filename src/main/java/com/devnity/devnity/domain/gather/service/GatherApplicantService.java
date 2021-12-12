package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
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
  private final GatherServiceUtils gatherServiceUtils;

  private final GatherApplicantRepository applicantRepository;

  @Transactional
  public String apply(Long userId, Long gatherId) {
    User user = UserServiceUtils.findUser(userRepository, userId);
    Gather gather = gatherServiceUtils.findGather(gatherId);

    // 자신의 게시물이거나 이미 신청했다면 신청할 수 없음
    if(userId.equals(gather.getUser().getId()) && applicantRepository.existsByUserAndGather(user, gather))
      throw new InvalidGatherApplyException();

    applicantRepository.save(GatherApplicant.of(user, gather));
    return "success";
  }

}
