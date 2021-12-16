package com.devnity.devnity.domain.gather.dto.request;

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
public class CreateGatherRequest {

  // Todo: Bean Validation

  private String title;

  private Integer applicantLimit;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate deadline;

  private String content;

  private GatherCategory category;

}
