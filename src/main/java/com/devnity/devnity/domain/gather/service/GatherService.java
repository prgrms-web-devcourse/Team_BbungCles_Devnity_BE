package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherService {

  private final UserRepository userRepository;
  private final GatherRepository gatherRepository;

  @Transactional
  public GatherStatus createGather(Long userId, CreateGatherRequest request) {
    User user = UserServiceUtils.findUser(userRepository, userId);
    Gather saved = gatherRepository.save(Gather.of(user, request));
    return saved.getStatus();
  }

}
