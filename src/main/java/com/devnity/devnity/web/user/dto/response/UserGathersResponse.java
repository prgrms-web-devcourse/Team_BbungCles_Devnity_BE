package com.devnity.devnity.web.user.dto.response;

import com.devnity.devnity.web.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.web.mapgakco.dto.SimpleMapgakcoInfoDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserGathersResponse {

  private List<SimpleGatherInfoDto> gathers;

  private List<SimpleMapgakcoInfoDto> mapgakcos;

  private UserGathersResponse(
    List<SimpleGatherInfoDto> gathers,
    List<SimpleMapgakcoInfoDto> mapgakcos) {
    this.gathers = gathers;
    this.mapgakcos = mapgakcos;
  }

  public static UserGathersResponse of(List<SimpleGatherInfoDto> gathers,
    List<SimpleMapgakcoInfoDto> mapgakcos) {
    return new UserGathersResponse(gathers, mapgakcos);
  }

}
