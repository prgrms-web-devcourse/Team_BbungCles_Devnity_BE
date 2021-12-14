package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateGatherResponse {
  private GatherStatus status;

  public static CreateGatherResponse of(GatherStatus status){
    return CreateGatherResponse.builder()
      .status(status)
      .build();
  }
}
