package com.devnity.devnity.domain.introduction.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.dto.response.SuggestResponse;
import com.devnity.devnity.domain.introduction.dto.response.UserDetailIntroductionResponse;
import com.devnity.devnity.domain.introduction.dto.response.UserIntroductionResponse;
import com.devnity.devnity.domain.introduction.service.IntroductionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/introductions")
@RestController
public class IntroductionController {

  private final IntroductionService introductionService;

  @GetMapping("/suggest")
  public ApiResponse<List<SuggestResponse>> suggest(
      @UserId Long userId) {
    return ApiResponse.ok(introductionService.suggest(userId));
  }

  @GetMapping
  public ApiResponse<CursorPageResponse<UserIntroductionResponse>> searchUserIntroductions(
      SearchIntroductionRequest searchRequest,
      @RequestParam(required = false) Long lastId,
      @RequestParam int size) {

    return ApiResponse.ok(
        introductionService.search(searchRequest, new CursorPageRequest(lastId, size)));
  }

  @GetMapping("/{introductionId}")
  public ApiResponse<UserDetailIntroductionResponse> getUserDetailIntroduction(
      @UserId Long userId, @PathVariable Long introductionId) {

    return ApiResponse.ok(introductionService.getUserDetailIntroduction(userId, introductionId));
  }
}
