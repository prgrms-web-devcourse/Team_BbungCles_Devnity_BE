package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.annotation.UserId;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentUpdateRequest;
import com.devnity.devnity.domain.mapgakco.service.mapgakcocomment.MapgakcoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoCommentController {

  private final MapgakcoCommentService mapgakcoCommentService;

  @PostMapping("/mapgakcos/{mapgakcoId}/comments")
  public ApiResponse<String> addComment(
    @UserId Long userId,
    @PathVariable Long mapgakcoId,
    @RequestBody MapgakcoCommentCreateRequest request
  ) {
    mapgakcoCommentService.create(mapgakcoId, userId, request);
    return ApiResponse.ok();
  }

  @PatchMapping("/mapgakcos/{mapgakcoId}/comments/{commentId}")
  public ApiResponse<String> updateComment(
    @PathVariable Long mapgakcoId,
    @PathVariable Long commentId,
    @RequestBody MapgakcoCommentUpdateRequest request
  ) {
    mapgakcoCommentService.update(commentId, request);
    return ApiResponse.ok();
  }

  @DeleteMapping("/mapgakcos/{mapgakcoId}/comments/{commentId}")
  public ApiResponse<String> deleteComment(
    @PathVariable Long mapgakcoId,
    @PathVariable Long commentId
  ) {
    mapgakcoCommentService.delete(commentId);
    return ApiResponse.ok();
  }

}


