package com.devnity.devnity.domain.mapgakco.dto.mapgakco.request;

import com.devnity.devnity.common.utils.annotation.Title;
import com.fasterxml.jackson.annotation.JsonFormat;
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

  @Title
  private String title;
  private Integer applicantLimit;
  private String content;
  private String location;
  private Double latitude;
  private Double longitude;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime meetingAt;

}
