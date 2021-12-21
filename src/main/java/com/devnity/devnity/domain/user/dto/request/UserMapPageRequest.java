package com.devnity.devnity.domain.user.dto.request;

import com.devnity.devnity.domain.user.entity.UserRole;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapPageRequest {

  private String course;
  private Integer generation;
  private UserRole role;
  private String name;

  @NotNull
  private Double lastDistance;
  @NotNull
  private Double centerX;
  @NotNull
  private Double centerY;
  @NotNull
  private Double currentNEX;
  @NotNull
  private Double currentNEY;
  @NotNull
  private Double currentSWX;
  @NotNull
  private Double currentSWY;

  @Builder
  public UserMapPageRequest(
    String course, Integer generation, UserRole role, String name,
    Double lastDistance, Double centerX, Double centerY,
    Double currentNEX, Double currentNEY, Double currentSWX, Double currentSWY) {
    this.course = course;
    this.generation = generation;
    this.role = role;
    this.name = name;
    this.lastDistance = lastDistance;
    this.centerX = centerX;
    this.centerY = centerY;
    this.currentNEX = currentNEX;
    this.currentNEY = currentNEY;
    this.currentSWX = currentSWX;
    this.currentSWY = currentSWY;
  }

}
