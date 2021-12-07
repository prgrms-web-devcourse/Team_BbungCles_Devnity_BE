package com.devnity.devnity.domain.user.controller;

import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public ResponseEntity<UserInfoResponse> getUserInfo(
      @AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
    return ResponseEntity.ok(userService.getUserInfoBy(jwtAuthentication.getUserId()));
  }
}
