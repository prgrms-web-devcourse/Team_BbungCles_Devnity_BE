package com.devnity.devnity.domain.auth.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.auth.dto.request.LoginRequest;
import com.devnity.devnity.domain.auth.dto.response.LoginResponse;
import com.devnity.devnity.domain.auth.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    return ApiResponse.ok(authService.login(request));
  }

}
