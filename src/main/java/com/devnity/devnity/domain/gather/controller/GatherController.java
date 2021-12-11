package com.devnity.devnity.domain.gather.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.service.GatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/gathers")
@RestController
public class GatherController {

  private final GatherService gatherService;

  @PostMapping
  public ApiResponse<GatherStatus> createGather(
    @AuthenticationPrincipal JwtAuthentication jwt,
    @RequestBody CreateGatherRequest request
  ) {
    GatherStatus response = gatherService.createGather(jwt.getUserId(), request);
    System.out.println(response);
    return ApiResponse.ok(response);
  }

}
