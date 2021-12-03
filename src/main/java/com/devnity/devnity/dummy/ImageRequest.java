package com.devnity.devnity.dummy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageRequest {
  private String imageBase64;
  private String dummy;
}
