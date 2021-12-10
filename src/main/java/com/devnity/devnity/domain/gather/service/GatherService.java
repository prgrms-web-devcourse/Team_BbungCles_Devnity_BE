package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherService {

  private final UserRepository userRepository;

  @Transactional
  public GatherStatus createGather(Long userId, CreateGatherRequest request) {
    // FIXME : UserUtil로 변경 + exception 변경
    User user = userRepository.findById(userId)
      .orElseThrow(EntityNotFoundException::new);

  }

}
