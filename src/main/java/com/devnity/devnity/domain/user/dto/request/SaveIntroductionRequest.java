package com.devnity.devnity.domain.user.dto.request;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.user.entity.Mbti;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveIntroductionRequest {

  private String profileImgUrl;
  private Mbti mbti;
  private String blogUrl;
  private String githubUrl;
  private String summary;
  private Double latitude;
  private Double longitude;
  private String description;

  @Builder
  public SaveIntroductionRequest(String profileImgUrl,
      Mbti mbti, String blogUrl, String githubUrl, String summary, Double latitude,
      Double longitude, String description) {
    this.profileImgUrl = profileImgUrl;
    this.mbti = mbti;
    this.blogUrl = blogUrl;
    this.githubUrl = githubUrl;
    this.summary = summary;
    this.latitude = latitude;
    this.longitude = longitude;
    this.description = description;
  }

  public Introduction toEntity() {
    return Introduction.builder()
        .blogUrl(blogUrl)
        .githubUrl(githubUrl)
        .latitude(latitude)
        .longitude(longitude)
        .mbti(mbti)
        .profileImgUrl(profileImgUrl)
        .summary(summary)
        .content(description)
        .build();
  }
}
