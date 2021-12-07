package com.devnity.devnity.domain.media.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.media.dto.MediaUrlResponse;
import com.devnity.devnity.domain.media.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
@RestController
public class MediaController {

  private final MediaService mediaService;

  @PostMapping
  public ResponseEntity<ApiResponse<MediaUrlResponse>> convertMedia(
    @RequestBody MultipartFile file
    ) {
    MediaUrlResponse response = mediaService.convertImage(file);
    return ResponseEntity.ok(ApiResponse.ok(response));
  }


}
