package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleGatherInfoDto {

  private Long gatherId;
  private GatherStatus status;
  private String title;
  private GatherCategory category;
  private LocalDateTime deadline;
  private LocalDateTime createdAt;
  private Integer applicantLimit;
  private int view;
  private int applicantCount;
  private int commentCount;
  private SimpleUserInfoDto author;

  public static SimpleGatherInfoDto of(Gather gather) {
    return SimpleGatherInfoDto.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus())
      .title(gather.getTitle())
      .category(gather.getCategory())
      .deadline(gather.getDeadline())
      .createdAt(gather.getCreatedAt())
      .view(gather.getView())
      .applicantLimit(gather.getApplicantLimit())
      .applicantCount(gather.getApplicantCount())
      .commentCount(gather.getCommentCount())
      .author(SimpleUserInfoDto.of(gather.getUser()))
      .build();
  }
}
