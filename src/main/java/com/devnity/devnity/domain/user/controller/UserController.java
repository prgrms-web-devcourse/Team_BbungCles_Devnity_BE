package com.devnity.devnity.domain.user.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.config.annotation.UserId;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;
  private final IntroductionService introductionService;

  @GetMapping("/me")
  public ApiResponse<UserInfoResponse> me(@UserId Long userId) {
    return ApiResponse.ok(userService.getUserInfo(userId));
  }

  @PostMapping
  public ApiResponse<String> signUp(@Valid @RequestBody SignUpRequest request) {
    userService.signUp(request);
    return ApiResponse.ok();
  }

  @PostMapping("/check")
  public ApiResponse<Map> checkEmail(@RequestBody String email) {
    return ApiResponse.ok(Collections.singletonMap("isDuplicated", userService.existsByEmail(email)));
  }

  @PutMapping("/me/introduction/{introductionId}")
  public ApiResponse<String> saveIntroduction(
      @UserId Long userId,
      @PathVariable Long introductionId,
      @RequestBody SaveIntroductionRequest request) {

    introductionService.save(userId, introductionId, request);
    return ApiResponse.ok();
  }
}
