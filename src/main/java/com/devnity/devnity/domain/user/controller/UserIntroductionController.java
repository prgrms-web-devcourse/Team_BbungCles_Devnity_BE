package com.devnity.devnity.domain.user.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.introduction.service.IntroductionService;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserIntroductionController {

  private final IntroductionService introductionService;

  @PutMapping("/me/introduction/{introductionId}")
  public ApiResponse<String> saveIntroduction(
    @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
    @PathVariable Long introductionId,
    @RequestBody SaveIntroductionRequest request) {

    introductionService.save(jwtAuthentication.getUserId(), introductionId, request);
    return ApiResponse.ok();
  }
}
