package com.devnity.devnity.dummy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DummyImageRequest {
  private String imageBase64;
  private String dummy;
}
