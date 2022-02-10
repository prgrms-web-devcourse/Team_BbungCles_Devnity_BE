package com.devnity.devnity.web.user.dto.request;

import com.devnity.devnity.domain.user.entity.UserRole;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapRequest {

  private String course;
  private Integer generation;
  private UserRole role;
  private String name;

  @NotNull
  private Double currentNEX;
  @NotNull
  private Double currentNEY;
  @NotNull
  private Double currentSWX;
  @NotNull
  private Double currentSWY;

  @Builder
  public UserMapRequest(
    String course, Integer generation, UserRole role, String name,
    Double currentNEX, Double currentNEY, Double currentSWX, Double currentSWY) {
    this.course = course;
    this.generation = generation;
    this.role = role;
    this.name = name;
    this.currentNEX = currentNEX;
    this.currentNEY = currentNEY;
    this.currentSWX = currentSWX;
    this.currentSWY = currentSWY;
  }

}
