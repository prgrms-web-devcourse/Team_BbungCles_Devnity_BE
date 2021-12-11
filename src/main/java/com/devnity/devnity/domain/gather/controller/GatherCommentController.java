package com.devnity.devnity.domain.gather.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.jwt.JwtAuthentication;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherCommentRequest;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/gathers/{gatherId}/comments")
@RestController
public class GatherCommentController {

  /**
   * 모집 게시글 댓글 추가
   */
  @PostMapping
  public ApiResponse<GatherCommentStatus> createComment(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId,
    @RequestBody CreateGatherCommentRequest request
  ){

    return ApiResponse.ok();
  }


  /**
   * 모집 게시글 댓글 수정
   */


  /**
   * 모집 게시글 댓글 삭제
   */
}
