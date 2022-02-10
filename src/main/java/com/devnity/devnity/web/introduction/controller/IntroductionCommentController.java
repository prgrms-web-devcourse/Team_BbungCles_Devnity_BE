package com.devnity.devnity.web.introduction.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.web.common.config.security.cors.annotation.UserId;
import com.devnity.devnity.web.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.web.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.web.introduction.dto.response.DeleteIntroductionCommentResponse;
import com.devnity.devnity.web.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.web.introduction.service.IntroductionCommentService;
import javax.validation.Valid;
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
      @RequestBody @Valid SaveIntroductionCommentRequest request) {

    return ApiResponse.ok(introductionCommentService.save(userId, introductionId, request));
  }

  @PatchMapping("/{commentId}")
  public ApiResponse<String> updateComment(
      @UserId Long userId,
      @PathVariable Long introductionId,
      @PathVariable Long commentId,
      @RequestBody @Valid UpdateIntroductionCommentRequest request) {

    introductionCommentService.update(userId, introductionId, commentId, request);

    return ApiResponse.ok();
  }

  @DeleteMapping("/{commentId}")
  public ApiResponse<DeleteIntroductionCommentResponse> deleteComment(
      @UserId Long userId, @PathVariable Long introductionId, @PathVariable Long commentId) {
    return ApiResponse.ok(introductionCommentService.delete(userId, introductionId, commentId));
  }

}
