package com.devnity.devnity.web.user.dto;

import com.devnity.devnity.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleUserMapInfoDto {

  private Long userId;
  private String name;
  private String course;
  private Integer generation;
  private String profileImgUrl;
  private Double latitude;
  private Double longitude;

  @Builder
  public SimpleUserMapInfoDto(Long userId, String name, String course, Integer generation,
    String profileImgUrl, Double latitude, Double longitude
  ) {
    this.userId = userId;
    this.name = name;
    this.course = course;
    this.generation = generation;
    this.profileImgUrl = profileImgUrl;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public static SimpleUserMapInfoDto of(User user, String profileImgUrl, Double latitude, Double longitude) {
    return SimpleUserMapInfoDto.builder()
      .userId(user.getId())
      .name(user.getName())
      .course(user.getCourseName())
      .generation(user.getGenerationSequence())
      .profileImgUrl(profileImgUrl)
      .latitude(latitude)
      .longitude(longitude)
      .build();
  }
}
