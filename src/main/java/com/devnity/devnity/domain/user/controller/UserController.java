package com.devnity.devnity.domain.user.controller;

import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
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
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping
  public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpRequest request) {
    userService.signUp(request);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{email}/check")
  public ResponseEntity<Map> checkEmail(@PathVariable String email) {
    return ResponseEntity.ok(Collections.singletonMap("isDuplicated", userService.existsByEmail(email)));
  }

}
