package com.devnity.devnity.domain.gather.dto.request;

import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@Getter
public class CreateGatherRequest {

  // Todo: Bean Validation

  private String title;

  private Integer applicantLimit;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime deadline;

  private String content;

  private GatherCategory category;

}
