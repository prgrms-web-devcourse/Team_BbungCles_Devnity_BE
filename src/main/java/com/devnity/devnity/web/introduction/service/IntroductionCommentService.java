package com.devnity.devnity.web.introduction.service;

import static com.devnity.devnity.common.error.exception.ErrorCode.INTRODUCTION_COMMENT_NOT_FOUND;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.web.introduction.dto.IntroductionCommentDto;
import com.devnity.devnity.web.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.web.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.web.introduction.dto.response.DeleteIntroductionCommentResponse;
import com.devnity.devnity.web.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IntroductionCommentService {

  private final IntroductionCommentRepository introductionCommentRepository;

  private final UserRetrieveService userRetrieveService;

  private final IntroductionRepository introductionRepository;

  @Transactional
  public SaveIntroductionCommentResponse save(Long userId, Long introductionId, SaveIntroductionCommentRequest request) {
    User user = userRetrieveService.getUser(userId);

    Introduction introduction = IntroductionServiceUtils.findIntroductionById(
      introductionRepository, introductionId);

    IntroductionComment parent = getParent(request.getParentId());

    IntroductionComment comment = introductionCommentRepository.save(
      request.toEntity(user, introduction, parent));

    return SaveIntroductionCommentResponse.of(comment, parent);
  }

  private IntroductionComment getParent(Long parentId) {

    if (parentId == null)
      return null;

    return introductionCommentRepository
        .findById(parentId)
        .orElseThrow(
            () ->
                new EntityNotFoundException(
                    String.format("There is no comment. id = %d", parentId), INTRODUCTION_COMMENT_NOT_FOUND));
  }

  @Transactional
  public void update(
      Long userId, Long introductionId, Long commentId, UpdateIntroductionCommentRequest request) {

    IntroductionComment comment = findCommentBy(userId, introductionId, commentId);

    comment.updateContent(request.getContent());
  }

  @Transactional
  public DeleteIntroductionCommentResponse delete(Long userId, Long introductionId, Long commentId) {
    IntroductionComment comment = findCommentBy(userId, introductionId, commentId);

    comment.delete();
    return new DeleteIntroductionCommentResponse(comment.getContent());
  }

  public List<IntroductionCommentDto> getCommentsBy(Long introductionId) {
    return introductionCommentRepository.findAllParentsByDesc(introductionId).stream()
        .map(
            comment ->
                IntroductionCommentDto.of(
                    comment, getChildren(comment), getSimpleUserInfo(comment)))
        .collect(Collectors.toList());
  }

  private SimpleUserInfoDto getSimpleUserInfo(IntroductionComment comment) {
    return userRetrieveService.getSimpleUserInfo(comment.getUser().getId());
  }

  private List<IntroductionCommentDto> getChildren(IntroductionComment parent) {
    return introductionCommentRepository.findAllChildrenByDesc(parent).stream()
        .map(comment -> IntroductionCommentDto.of(comment, getSimpleUserInfo(comment)))
        .collect(Collectors.toList());
  }

  private IntroductionComment findCommentBy(Long userId, Long introductionId, Long commentId) {
    return introductionCommentRepository
      .findByIdAndUserIdAndIntroductionId(commentId, userId, introductionId)
      .orElseThrow(
        () ->
          new EntityNotFoundException(
            String.format(
              "There is no comment. userId = %d, introductionId = %d, commentId = %d",
              userId, introductionId, commentId), INTRODUCTION_COMMENT_NOT_FOUND));
  }

  public long countBy(Long introductionId) {
    return introductionCommentRepository.countBy(introductionId);
  }
}
