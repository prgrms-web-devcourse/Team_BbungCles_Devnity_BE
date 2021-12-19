package com.devnity.devnity.domain.user.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapRequest {

  @NotNull
  private Double currentNEX;
  @NotNull
  private Double currentNEY;
  @NotNull
  private Double currentSWX;
  @NotNull
  private Double currentSWY;

  @Builder
  public UserMapRequest(Double currentNEX, Double currentNEY, Double currentSWX, Double currentSWY) {
    this.currentNEX = currentNEX;
    this.currentNEY = currentNEY;
    this.currentSWX = currentSWX;
    this.currentSWY = currentSWY;
  }

}
