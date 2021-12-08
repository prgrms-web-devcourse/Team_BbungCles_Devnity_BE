package com.devnity.devnity.domain.media.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MediaUrlResponse {

  private String mediaUrl;

  @Builder(access = AccessLevel.PRIVATE)
  public MediaUrlResponse(String mediaUrl) {
    this.mediaUrl = mediaUrl;
  }

  public static MediaUrlResponse of(String mediaUrl) {
    return MediaUrlResponse.builder()
      .mediaUrl(mediaUrl)
      .build();
  }

}
