package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherCommentRequest;
import com.devnity.devnity.domain.gather.dto.request.UpdateGatherCommentRequest;
import com.devnity.devnity.domain.gather.dto.response.CreateGatherCommentResponse;
import com.devnity.devnity.domain.gather.dto.response.UpdateGatherCommentResponse;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.gather.event.CreateGatherCommentEvent;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherCommentService {

  private final ApplicationEventPublisher publisher;

  private final UserRetrieveService userRetrieveService;
  private final GatherRetrieveService gatherRetrieveService;

  private final GatherCommentRepository commentRepository;

  @Transactional
  public CreateGatherCommentResponse createComment(Long userId, Long gatherId, CreateGatherCommentRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather gather = gatherRetrieveService.getGather(gatherId);

    GatherComment.GatherCommentBuilder commentBuilder = GatherComment.builder();
    commentBuilder
      .user(me)
      .gather(gather)
      .content(request.getContent());

    Long parentId = request.getParentId();
    if (parentId != null) {
      GatherComment parent = gatherRetrieveService.getComment(parentId);
      commentBuilder.parent(parent);
    }

    publisher.publishEvent(new CreateGatherCommentEvent(gatherId));
    return CreateGatherCommentResponse.of(commentRepository.save(commentBuilder.build()));
  }

  @Transactional
  public UpdateGatherCommentResponse updateComment(Long userId, Long commentId, UpdateGatherCommentRequest request) {
    GatherComment comment = gatherRetrieveService.getComment(commentId);

    if (!comment.isWrittenBy(userId)) {
      throw new InvalidValueException(
        String.format("작성자만이 댓글을 수정할 수 있음 (commentId : %d, userID : %d)", commentId, userId),
        ErrorCode.GATHER_UPDATE_NOT_ALLOWED
      );
    }
    comment.update(request.getContent());
    commentRepository.flush();
    return UpdateGatherCommentResponse.of(gatherRetrieveService.getComment(commentId));
  }

}
