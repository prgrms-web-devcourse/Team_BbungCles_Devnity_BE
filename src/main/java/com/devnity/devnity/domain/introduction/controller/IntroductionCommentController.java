package com.devnity.devnity.domain.introduction.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.annotation.UserId;
import com.devnity.devnity.domain.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.response.DeleteIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.service.IntroductionCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/introductions/{introductionId}/comments")
@RestController
public class IntroductionCommentController {

  private final IntroductionCommentService introductionCommentService;

  @PostMapping
  public ApiResponse<SaveIntroductionCommentResponse> createComment(
      @UserId Long userId,
      @PathVariable Long introductionId,
      @RequestBody SaveIntroductionCommentRequest request) {

    return ApiResponse.ok(introductionCommentService.save(userId, introductionId, request));
  }

  @PatchMapping("/{commentId}")
  public ApiResponse<String> updateComment(
      @UserId Long userId,
      @PathVariable Long introductionId,
      @PathVariable Long commentId,
      @RequestBody UpdateIntroductionCommentRequest request) {

    introductionCommentService.update(userId, introductionId, commentId, request);

    return ApiResponse.ok();
  }

  @DeleteMapping("/{commentId}")
  public ApiResponse<DeleteIntroductionCommentResponse> deleteComment(
      @UserId Long userId, @PathVariable Long introductionId, @PathVariable Long commentId) {
    return ApiResponse.ok(introductionCommentService.delete(userId, introductionId, commentId));
  }

}
