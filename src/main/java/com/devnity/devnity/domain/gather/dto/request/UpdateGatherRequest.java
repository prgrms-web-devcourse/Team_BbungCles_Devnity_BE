package com.devnity.devnity.domain.gather.dto.request;

import com.devnity.devnity.web.common.utils.annotation.Title;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class UpdateGatherRequest {

  @Title
  private String title;
  private String content;
  private GatherCategory category;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate deadline;

}
