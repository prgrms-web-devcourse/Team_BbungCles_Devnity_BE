package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherStatusResponse {
  private Long gatherId;
  private GatherStatus status;

  public static GatherStatusResponse of(Gather gather){
    return GatherStatusResponse.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus())
      .build();
  }
}
