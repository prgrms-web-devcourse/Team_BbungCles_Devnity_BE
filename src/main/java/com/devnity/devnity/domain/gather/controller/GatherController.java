package com.devnity.devnity.domain.gather.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.dto.response.GatherCardResponse;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.service.GatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/gathers")
@RestController
public class GatherController {

  private final GatherService gatherService;

  /**
   * 모집 게시글 생성하기
   */
  @PostMapping
  public ApiResponse<GatherStatus> createGather(
    @UserId Long userId,
    @RequestBody CreateGatherRequest request
  ) {
    GatherStatus response = gatherService.createGather(userId, request);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 수정하기
   */

  /**
   * 모집 게시글 삭제하기
   */


  /**
   * 모집 게시글 추천 조회
   */


  /**
   * 모집 게시글 페이징 조회
   */
  @GetMapping
  public ApiResponse<CursorPageResponse<GatherCardResponse>> getGatherCards(
    @RequestParam(value = "category", required = false) GatherCategory category,
    CursorPageRequest pageRequest
  ) {

  }

  /**
   * 모집 게시글 상세 조회
   */


}
