package com.devnity.devnity.domain.mapgakco.dto.mapgakco.request;

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
public class MapgakcoCreateRequest {

  private String title;
  private Integer applicantLimit;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime deadline;
  private String content;
  private String location;
  private Double latitude;
  private Double longitude;
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime meetingAt;

}
