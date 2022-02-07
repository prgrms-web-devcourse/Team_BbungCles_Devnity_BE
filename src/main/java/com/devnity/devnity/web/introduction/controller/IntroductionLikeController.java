package com.devnity.devnity.web.introduction.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.annotation.UserId;
import com.devnity.devnity.domain.introduction.service.IntroductionLikeService;
import java.util.Collections;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/introductions/{introductionId}/like")
@RestController
public class IntroductionLikeController {

  private final IntroductionLikeService introductionLikeService;

  @PostMapping
  public ApiResponse<Map> like(@UserId Long userId, @PathVariable Long introductionId) {

    return ApiResponse.ok(
      Collections.singletonMap("isLiked", introductionLikeService.like(userId, introductionId)));
  }

  @DeleteMapping
  public ApiResponse<Map> removeLike(@UserId Long userId, @PathVariable Long introductionId) {

    return ApiResponse.ok(
      Collections.singletonMap("isLiked", introductionLikeService.removeLike(userId, introductionId)));
  }
}
