package com.devnity.devnity.domain.gather.dto.request;

import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Getter
public class CreateGatherRequest {

  // Todo: Bean Validation

  private String title;

  private Integer applicantLimit;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime deadline;

  private String content;

  private GatherCategory category;

}
