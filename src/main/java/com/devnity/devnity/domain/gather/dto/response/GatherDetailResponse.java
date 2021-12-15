package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.dto.GatherCommentDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
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
  private GatherCategory category;
  private LocalDateTime deadline;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Integer applicantLimit;
  private int view;
  private int applicantCount;
  private int commentCount;

  private boolean isApplied;
  private List<SimpleUserInfoDto> participants;

  private List<GatherCommentDto> comments;

  public static GatherDetailResponse of(Gather gather, boolean isApplied, List<GatherCommentDto> comments) {
    return GatherDetailResponse.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus())
      .title(gather.getTitle())
      .category(gather.getCategory())
      .deadline(gather.getDeadline())
      .createdAt(gather.getCreatedAt())
      .modifiedAt(gather.getModifiedAt())
      .applicantLimit(gather.getApplicantLimit())
      .view(gather.getView())
      .applicantCount(gather.getApplicantCount())
      .commentCount(gather.getCommentCount())
      .isApplied(isApplied)
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
