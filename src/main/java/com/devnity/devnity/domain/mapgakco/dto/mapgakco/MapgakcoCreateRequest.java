package com.devnity.devnity.domain.mapgakco.dto.mapgakco;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoCreateRequest {

  private String title;
  private Integer applicantLimit;
  private LocalDateTime deadline;
  private String content;
  private String location;
  private Double latitude;
  private Double longitude;
  private LocalDateTime meetingDateTime;

}
