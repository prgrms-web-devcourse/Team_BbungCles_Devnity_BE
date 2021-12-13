package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.dto.request.CreateGatherCommentRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.gather.exception.GatherCommentNotFoundException;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherCommentService {

  private final UserRepository userRepository;
  private final GatherRetrieveService gatherRetrieveService;

  private final GatherCommentRepository commentRepository;

  @Transactional
  public GatherCommentStatus createComment(Long userId, Long gatherId, CreateGatherCommentRequest request){
    User user = UserServiceUtils.findUser(userRepository, userId);
    Gather gather = gatherRetrieveService.getGather(gatherId);

    GatherComment.GatherCommentBuilder commentBuilder = GatherComment.builder();
    commentBuilder
      .user(user)
      .gather(gather)
      .content(request.getContent());

    Long parentId = request.getParentId();
    if(parentId != null){
      GatherComment parent = commentRepository.findById(parentId)
        .orElseThrow(GatherCommentNotFoundException::new);
      commentBuilder.parent(parent);
    }

    GatherComment comment = commentBuilder.build();
    return comment.getStatus();
  }

}
