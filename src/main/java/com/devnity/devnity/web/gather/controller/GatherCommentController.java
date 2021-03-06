package com.devnity.devnity.web.gather.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.web.common.config.security.cors.annotation.UserId;
import com.devnity.devnity.web.gather.dto.request.CreateGatherCommentRequest;
import com.devnity.devnity.web.gather.dto.request.UpdateGatherCommentRequest;
import com.devnity.devnity.web.gather.dto.response.CreateGatherCommentResponse;
import com.devnity.devnity.web.gather.dto.response.DeleteGatherCommentResponse;
import com.devnity.devnity.web.gather.service.GatherCommentService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/gathers/{gatherId}/comments")
@RestController
public class GatherCommentController {

  private final GatherCommentService commentService;

  /**
   * 모집 게시글 댓글 추가
   */
  @PostMapping
  public ApiResponse<CreateGatherCommentResponse> createComment(
    @UserId Long userId,
    @PathVariable("gatherId") Long gatherId,
    @Valid @RequestBody CreateGatherCommentRequest request
  ) {
    CreateGatherCommentResponse response = commentService.createComment(userId, gatherId, request);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 댓글 수정
   */
  @PatchMapping("/{commentId}")
  public ApiResponse<String> createComment(
    @UserId Long userId,
    @PathVariable("commentId") Long commentId,
    @Valid @RequestBody UpdateGatherCommentRequest request
  ) {
    String response = commentService.updateComment(userId, commentId, request);
    return ApiResponse.ok(response);
  }

  /**
   * 모집 게시글 댓글 삭제
   */
  @DeleteMapping("/{commentId}")
  public ApiResponse<DeleteGatherCommentResponse> deleteComment(
    @UserId Long userId,
    @PathVariable("commentId") Long commentId
  ) {
    DeleteGatherCommentResponse response = commentService.deleteComment(userId, commentId);
    return ApiResponse.ok(response);
  }

}
