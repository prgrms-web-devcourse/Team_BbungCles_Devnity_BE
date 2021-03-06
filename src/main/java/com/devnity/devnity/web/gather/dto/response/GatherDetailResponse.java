package com.devnity.devnity.web.gather.dto.response;

import com.devnity.devnity.web.gather.dto.GatherCommentDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GatherDetailResponse {

  private Long gatherId;
  private GatherStatus status;
  private String title;
  private String content;
  private GatherCategory category;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime deadline;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime modifiedAt;

  private Integer applicantLimit;
  private int view;
  private int applicantCount;
  private int commentCount;

  private Boolean isApplied;

  private SimpleUserInfoDto author;

  private List<SimpleUserInfoDto> participants;

  private List<GatherCommentDto> comments;

  public static GatherDetailResponse of(Gather gather, boolean isApplied, List<GatherCommentDto> comments) {
    return GatherDetailResponse.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus())
      .title(gather.getTitle())
      .content(gather.getContent())
      .category(gather.getCategory())
      .deadline(gather.getDeadline().getDeadline())
      .createdAt(gather.getCreatedAt())
      .modifiedAt(gather.getModifiedAt())
      .applicantLimit(gather.getApplicantLimit())
      .view(gather.getView())
      .applicantCount(gather.getApplicantCount())
      .commentCount(gather.getCommentCount())
      .isApplied(isApplied)
      .author(SimpleUserInfoDto.of(gather.getUser()))
      .participants(
        gather.getApplicants().stream()
          .map(applicant -> applicant.getUser())
          .map(user -> SimpleUserInfoDto.of(user, user.getIntroduction().getProfileImgUrl()))
          .collect(Collectors.toList())
      )
      .comments(comments)
      .build();
  }
}
