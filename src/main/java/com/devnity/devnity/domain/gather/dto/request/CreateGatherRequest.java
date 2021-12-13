package com.devnity.devnity.domain.gather.dto.request;

import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateGatherRequest {

  // Todo: Bean Validation

  private String title;

  private Integer applicantLimit;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime deadline;

  private String content;

  private GatherCategory category;

  @Builder
  public CreateGatherRequest(String title, Integer applicantLimit, LocalDateTime deadline,
    String content, GatherCategory category) {
    this.title = title;
    this.applicantLimit = applicantLimit;
    this.deadline = deadline;
    this.content = content;
    this.category = category;
  }
}
