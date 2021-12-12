package com.devnity.devnity.domain.gather.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.gather.service.GatherApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/gathers/{gatherId}/apply")
@RestController
public class GatherApplicantController {

  private final GatherApplicantService applicantService;

  /**
   * 모집 신청
   */
  @PostMapping
  public ApiResponse<String> apply(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId
  ) {
    String response = applicantService.apply(userId, gatherId);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 신청 취소
   */
  @DeleteMapping
  public ApiResponse<String> cancel(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId
  ) {
    String response = applicantService.cancel(userId, gatherId);
    return ApiResponse.ok(response);
  }

}
