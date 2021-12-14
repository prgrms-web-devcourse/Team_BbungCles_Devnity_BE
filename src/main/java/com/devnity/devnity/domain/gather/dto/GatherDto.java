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
public class GatherDto {

  private Long gatherId;

  private GatherStatus status;

  private String title;

  private GatherCategory category;

  private int view;

  private LocalDateTime deadline;

  private Integer applicantLimit;

  private int applicantCount;

  private int commentCount;

  private SimpleUserInfoDto simpleUserInfo;

  public GatherDto of(Gather gather){
    GatherDto
  }
}
