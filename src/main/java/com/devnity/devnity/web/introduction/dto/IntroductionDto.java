package com.devnity.devnity.web.introduction.dto;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.user.entity.Mbti;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IntroductionDto {

  private Long introductionId;
  private String profileImgUrl;
  private Mbti mbti;
  private String blogUrl;
  private String githubUrl;
  private String summary;
  private Double latitude;
  private Double longitude;
  private Long likeCount;
  private Long commentCount;
  private String description;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @Builder
  private IntroductionDto(Long introductionId, String profileImgUrl,
    Mbti mbti, String blogUrl, String githubUrl, String summary, Double latitude,
    Double longitude, Long likeCount, Long commentCount, String description,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.introductionId = introductionId;
    this.profileImgUrl = profileImgUrl;
    this.mbti = mbti;
    this.blogUrl = blogUrl;
    this.githubUrl = githubUrl;
    this.summary = summary;
    this.latitude = latitude;
    this.longitude = longitude;
    this.likeCount = likeCount;
    this.commentCount = commentCount;
    this.description = description;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static IntroductionDto of(Introduction introduction, long likeCount, long commentCount) {
    return IntroductionDto.builder()
        .introductionId(introduction.getId())
        .profileImgUrl(introduction.getProfileImgUrl())
        .mbti(introduction.getMbti())
        .blogUrl(introduction.getBlogUrl())
        .githubUrl(introduction.getGithubUrl())
        .summary(introduction.getSummary())
        .latitude(introduction.getLatitude())
        .longitude(introduction.getLongitude())
        .likeCount(likeCount)
        .commentCount(commentCount)
        .createdAt(introduction.getCreatedAt())
        .updatedAt(introduction.getModifiedAt())
        .build();
  }

  public static IntroductionDto of(Introduction introduction, String description, long likeCount, long commentCount) {
    return IntroductionDto.builder()
        .introductionId(introduction.getId())
        .profileImgUrl(introduction.getProfileImgUrl())
        .mbti(introduction.getMbti())
        .blogUrl(introduction.getBlogUrl())
        .githubUrl(introduction.getGithubUrl())
        .summary(introduction.getSummary())
        .latitude(introduction.getLatitude())
        .longitude(introduction.getLongitude())
        .likeCount(likeCount)
        .commentCount(commentCount)
        .createdAt(introduction.getCreatedAt())
        .updatedAt(introduction.getModifiedAt())
        .description(description)
        .build();
  }

  public static IntroductionDto of(Introduction introduction, String description) {
    return IntroductionDto.builder()
      .introductionId(introduction.getId())
      .profileImgUrl(introduction.getProfileImgUrl())
      .mbti(introduction.getMbti())
      .blogUrl(introduction.getBlogUrl())
      .githubUrl(introduction.getGithubUrl())
      .summary(introduction.getSummary())
      .latitude(introduction.getLatitude())
      .longitude(introduction.getLongitude())
      .createdAt(introduction.getCreatedAt())
      .updatedAt(introduction.getModifiedAt())
      .description(description)
      .build();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("IntroductionDto{");
    sb.append("introductionId=").append(introductionId);
    sb.append(", profileImgUrl='").append(profileImgUrl).append('\'');
    sb.append(", mbti=").append(mbti);
    sb.append(", blogUrl='").append(blogUrl).append('\'');
    sb.append(", githubUrl='").append(githubUrl).append('\'');
    sb.append(", summary='").append(summary).append('\'');
    sb.append(", latitude=").append(latitude);
    sb.append(", longitude=").append(longitude);
    sb.append(", createdAt=").append(createdAt);
    sb.append(", updatedAt=").append(updatedAt);
    sb.append('}');
    return sb.toString();
  }
}
