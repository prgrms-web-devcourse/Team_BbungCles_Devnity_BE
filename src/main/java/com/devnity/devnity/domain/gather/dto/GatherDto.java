package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GatherDto {

  private Long gatherId;
  private String status;
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

  public static GatherDto of(Gather gather, boolean isApplied, List<SimpleUserInfoDto> participants, List<GatherCommentDto> comments){
    GatherDto.builder()
      .gatherId()
      .status()
      .
  }
}
