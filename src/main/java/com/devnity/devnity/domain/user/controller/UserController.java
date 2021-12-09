package com.devnity.devnity.domain.user.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.introduction.service.IntroductionService;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.service.UserService;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  private final IntroductionService introductionService;

  @GetMapping("/me")
  public ApiResponse<UserInfoResponse> getUserInfo(
      @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
    return ApiResponse.ok(userService.getUserInfoBy(jwtAuthentication.getUserId()));
  }

  @PostMapping
  public ApiResponse<String> signUp(@Valid @RequestBody SignUpRequest request) {
    userService.signUp(request);
    return ApiResponse.ok();
  }

  @GetMapping("/check")
  public ApiResponse<Map> checkEmail(@RequestParam("email") String email) {
    return ApiResponse.ok(Collections.singletonMap("isDuplicated", userService.existsByEmail(email)));
  }

  @PutMapping("/me/introduction/{introductionId}")
  public ApiResponse<String> saveIntroduction(
      @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
      @PathVariable Long introductionId,
      @RequestBody SaveIntroductionRequest request) {

    introductionService.save(jwtAuthentication.getUserId(), introductionId, request);
    return ApiResponse.ok();
  }
}
