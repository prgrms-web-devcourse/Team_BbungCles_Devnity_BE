package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SuggestGatherResponse {

  private List<SimpleGatherInfoDto> gathers;

  public static SuggestGatherResponse of(List<SimpleGatherInfoDto> gathers) {
    return SuggestGatherResponse.builder()
      .gathers(gathers)
      .build();
  }

}
