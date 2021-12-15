package com.devnity.devnity.domain.mapgakco.dto;

import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class SimpleMapgakcoInfoDto {

  private Long mapgakcoId;
  private MapgakcoStatus status;
  private String title;
  private String location;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime deadline;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime meetingAt;

  private Integer applicantLimit;
  private Integer applicantCount;

  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime createdAt;
}