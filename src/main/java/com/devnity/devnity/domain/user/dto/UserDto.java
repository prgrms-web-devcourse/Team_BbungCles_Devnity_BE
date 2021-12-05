package com.devnity.devnity.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {

  private Long userId;
  private String name;
  private String course;
  private int generation;
  private String profileImgUrl;
  private String role;

  @Builder
  public UserDto(Long userId, String name, String course, int generation,
      String profileImgUrl, String role) {
    this.userId = userId;
    this.name = name;
    this.course = course;
    this.generation = generation;
    this.profileImgUrl = profileImgUrl;
    this.role = role;
  }
}
