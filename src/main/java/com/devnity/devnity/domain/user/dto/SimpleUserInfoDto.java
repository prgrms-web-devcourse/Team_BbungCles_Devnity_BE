package com.devnity.devnity.domain.user.dto;

import com.devnity.devnity.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SimpleUserInfoDto {

  private Long userId;
  private String name;
  private String course;
  private int generation;
  private String profileImgUrl;
  private String role;

  @Builder
  public SimpleUserInfoDto(Long userId, String name, String course, int generation,
      String profileImgUrl, String role) {
    this.userId = userId;
    this.name = name;
    this.course = course;
    this.generation = generation;
    this.profileImgUrl = profileImgUrl;
    this.role = role;
  }

  public static SimpleUserInfoDto of(User user, String profileImgUrl) {
    return SimpleUserInfoDto.builder()
        .userId(user.getId())
        .course(user.getCourseName())
        .generation(user.getGenerationSequence())
        .name(user.getName())
        .role(user.getRole().toString())
        .profileImgUrl(profileImgUrl)
        .build();
  }

  public static SimpleUserInfoDto of(User user){
    return SimpleUserInfoDto.builder()
      .userId(user.getId())
      .course(user.getCourseName())
      .generation(user.getGenerationSequence())
      .name(user.getName())
      .role(user.getRole().toString())
      .profileImgUrl(user.getIntroduction().getProfileImgUrl())
      .build();
  }
}
