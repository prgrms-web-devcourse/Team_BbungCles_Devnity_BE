package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherCommentRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherCommentService {

  private final UserRepository userRepository;
  private final GatherRepository gatherRepository;
  private final GatherCommentRepository commentRepository;

  @Transactional
  public GatherCommentStatus createComment(Long userId, Long gatherId, CreateGatherCommentRequest request){
    User user = UserServiceUtils.findUser(userRepository, userId);
    Optional<Gather> byId = gatherRepository.findById(gatherId);
  }

}
