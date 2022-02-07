package com.devnity.devnity.web.media.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageUrlResponse {

  private String imageUrl;

  @Builder(access = AccessLevel.PRIVATE)
  public ImageUrlResponse(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public static ImageUrlResponse of(String imageUrl) {
    return ImageUrlResponse.builder()
      .imageUrl(imageUrl)
      .build();
  }

}
