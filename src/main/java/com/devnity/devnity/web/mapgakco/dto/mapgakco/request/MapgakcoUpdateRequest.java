package com.devnity.devnity.web.mapgakco.dto.mapgakco.request;

import com.devnity.devnity.web.common.utils.annotation.Title;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class MapgakcoUpdateRequest {

  @Title
  private String title;
  @NotBlank
  private String content;
  @NotNull
  private String location;
  @NotNull
  private Double latitude;
  @NotNull
  private Double longitude;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime meetingAt;

}
