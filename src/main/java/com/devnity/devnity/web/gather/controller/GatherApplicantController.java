package com.devnity.devnity.web.gather.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.web.common.config.security.cors.annotation.UserId;
import com.devnity.devnity.web.gather.service.GatherApplicantService;
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
    String response = applicantService.createApplicant(userId, gatherId);
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
    String response = applicantService.deleteApplicant(userId, gatherId);
    return ApiResponse.ok(response);
  }

}
