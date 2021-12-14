package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.service.mapgakcocomment.MapgakcoCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoCommnetV1Controller {

  private final MapgakcoCommentService mapgakcoCommentService;

  @PostMapping("/mapgakcos/{id}/comments")
  public ApiResponse<String> addComment(
    @PathVariable Long mapgakcoId,
    @UserId Long userId,
    @RequestBody MapgakcoCommentCreateRequest request
  ) {
    mapgakcoCommentService.create(mapgakcoId, userId, request);
    return ApiResponse.ok();
  }

}
