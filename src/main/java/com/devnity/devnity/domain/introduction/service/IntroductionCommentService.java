package com.devnity.devnity.domain.introduction.service;

import com.devnity.devnity.domain.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.exception.IntroductionCommentNotFoundException;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IntroductionCommentService {

  private final IntroductionCommentRepository introductionCommentRepository;

  private final UserRepository userRepository;

  private final IntroductionRepository introductionRepository;

  @Transactional
  public SaveIntroductionCommentResponse save(Long userId, Long introductionId, SaveIntroductionCommentRequest request) {
    User user = UserServiceUtils.findUser(userRepository, userId);

    Introduction introduction = IntroductionServiceUtils.findIntroductionByIdAndUserId(
      introductionRepository, introductionId, userId);

    IntroductionComment parent = findParentComment(request.getParentId());

    IntroductionComment comment = introductionCommentRepository.save(
      request.toEntity(user, introduction, parent));

    return SaveIntroductionCommentResponse.of(comment, parent);
  }

  private IntroductionComment findParentComment(Long parentId) {

    if (Objects.isNull(parentId))
      return null;

    return introductionCommentRepository
        .findById(parentId)
        .orElseThrow(
            () ->
                new IntroductionCommentNotFoundException(
                    String.format("There is no comment. id = %d", parentId)));
  }

  @Transactional
  public void update(
      Long userId, Long introductionId, Long commentId, UpdateIntroductionCommentRequest request) {

    IntroductionComment comment = findCommentBy(userId, introductionId, commentId);

    comment.updateContent(request.getContent());
  }

  @Transactional
  public void delete(Long userId, Long introductionId, Long commentId) {
    IntroductionComment comment = findCommentBy(userId, introductionId, commentId);

    comment.delete();
  }

  private IntroductionComment findCommentBy(Long userId, Long introductionId, Long commentId) {
    return introductionCommentRepository
      .findByIdAndUserIdAndIntroductionId(commentId, userId, introductionId)
      .orElseThrow(
        () ->
          new IntroductionCommentNotFoundException(
            String.format(
              "There is no comment. userId = %d, introductionId = %d, commentId = %d",
              userId, introductionId, commentId)));
  }
}
