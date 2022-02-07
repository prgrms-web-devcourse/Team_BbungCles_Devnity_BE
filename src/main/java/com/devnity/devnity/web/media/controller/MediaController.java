package com.devnity.devnity.web.media.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.media.dto.ImageUrlResponse;
import com.devnity.devnity.domain.media.dto.MediaUrlResponse;
import com.devnity.devnity.domain.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
@RestController
public class MediaController {

  private final MediaService mediaService;

  static final String MEDIA_DIR = "media";
  static final String IMAGE_DIR = "img";
  static final String PROFILE_IMAGE_DIR = "img/profile";

  /**
   * 사용자가 요청한 미디어 파일을 CloudFront url로 반환
   */
  @PostMapping
  public ApiResponse<MediaUrlResponse> uploadMedia(
    @RequestPart(value = "media", required = false) MultipartFile file
    ) {
    MediaUrlResponse response = mediaService.uploadMedia(file, MEDIA_DIR);
    return ApiResponse.ok(response);
  }

  /**
   * 사용자가 요청한 프로필 이미지 파일을 CloudFront url로 반환
   */
  @PostMapping("/image/profile")
  public ApiResponse<ImageUrlResponse> uploadProfileImage(
    @RequestPart(value = "profileImage", required = false) MultipartFile file
  ) {
    ImageUrlResponse response = mediaService.uploadImage(file, PROFILE_IMAGE_DIR);
    return ApiResponse.ok(response);
  }

}
