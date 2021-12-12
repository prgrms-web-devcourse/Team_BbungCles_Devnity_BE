package com.devnity.devnity.domain.introduction.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.service.IntroductionCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/introductions")
@RestController
public class IntroductionCommentController {

  private final IntroductionCommentService introductionCommentService;

  @PostMapping("/{introductionId}/comments")
  public ApiResponse<SaveIntroductionCommentResponse> createComment(
      @UserId Long userId,
      @PathVariable Long introductionId,
      @RequestBody SaveIntroductionCommentRequest request) {

    return ApiResponse.ok(introductionCommentService.save(userId, introductionId, request));
  }

  @PatchMapping("/{introductionId}/comments/{commentId}")
  public ApiResponse<String> updateComment(
      @UserId Long userId,
      @PathVariable Long introductionId,
      @PathVariable Long commentId,
      @RequestBody UpdateIntroductionCommentRequest request) {

    introductionCommentService.update(userId, introductionId, commentId, request);

    return ApiResponse.ok();
  }
}
