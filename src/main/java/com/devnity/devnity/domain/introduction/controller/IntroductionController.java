package com.devnity.devnity.domain.introduction.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.introduction.dto.response.SuggestResponse;
import com.devnity.devnity.domain.introduction.service.IntroductionLikeService;
import com.devnity.devnity.domain.introduction.service.IntroductionService;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/introductions")
@RestController
public class IntroductionController {

  private final IntroductionService introductionService;

  private final IntroductionLikeService introductionLikeService;

  @GetMapping("/suggest")
  public ApiResponse<List<SuggestResponse>> suggest(
      @UserId Long userId) {
    return ApiResponse.ok(introductionService.suggest(userId));
  }

}
