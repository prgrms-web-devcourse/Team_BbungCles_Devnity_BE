package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherStatusResponse {
  private GatherStatus status;

  public static GatherStatusResponse of(GatherStatus status){
    return GatherStatusResponse.builder()
      .status(status)
      .build();
  }
}
