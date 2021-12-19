package com.devnity.devnity.domain.gather.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.common.config.security.annotation.UserId;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.dto.request.UpdateGatherRequest;
import com.devnity.devnity.domain.gather.dto.response.GatherDetailResponse;
import com.devnity.devnity.domain.gather.dto.response.GatherStatusResponse;
import com.devnity.devnity.domain.gather.dto.response.SuggestGatherResponse;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.service.GatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ApiResponse<GatherStatusResponse> createGather(
    @UserId Long userId,
    @RequestBody CreateGatherRequest request
  ) {
    GatherStatusResponse response = gatherService.createGather(userId, request);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 수정하기
   */
  @PatchMapping("/{gatherId}")
  public ApiResponse<GatherStatusResponse> updateGather(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId,
    @RequestBody UpdateGatherRequest request
  ) {
    GatherStatusResponse response = gatherService.updateGather(userId, gatherId, request);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 삭제하기
   */
  @DeleteMapping("/{gatherId}")
  public ApiResponse<GatherStatusResponse> deleteGather(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId
  ) {
    GatherStatusResponse response = gatherService.deleteGather(userId, gatherId);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 마감
   */
  @PatchMapping("/{gatherId}/close")
  public ApiResponse<GatherStatusResponse> closeGather(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId
  ) {
    GatherStatusResponse response = gatherService.closeGather(userId, gatherId);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 추천 조회
   */
  @GetMapping("/suggest")
  public ApiResponse<SuggestGatherResponse> suggestGather() {
    SuggestGatherResponse response = gatherService.suggestGather();
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 메뉴바 조회
   */
  @GetMapping
  public ApiResponse<CursorPageResponse<SimpleGatherInfoDto>> lookUpGatherBoard(
    @RequestParam(value = "title", required = false) String title,
    @RequestParam(value = "category", required = false) GatherCategory category,
    CursorPageRequest pageRequest
  ) {
    CursorPageResponse<SimpleGatherInfoDto> response = gatherService.lookUpGatherBoard(title, category, pageRequest);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 상세 조회
   */
  @GetMapping("/{gatherId}")
  public ApiResponse<GatherDetailResponse> lookUpGatherDetail(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId
  ) {
    GatherDetailResponse response = gatherService.lookUpGatherDetail(userId, gatherId);
    return ApiResponse.ok(response);
  }

}
