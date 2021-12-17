package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SimpleGatherInfoDto {

  private Long gatherId;
  private GatherStatus status;
  private String title;
  private GatherCategory category;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime deadline;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdAt;

  private Integer applicantLimit;
  private int view;
  private int applicantCount;
  private int commentCount;
  private SimpleUserInfoDto author;

  public static SimpleGatherInfoDto of(Gather gather) {
    return SimpleGatherInfoDto.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus())
      .title(gather.getTitle())
      .category(gather.getCategory())
      .deadline(gather.getDeadline().getDeadline())
      .createdAt(gather.getCreatedAt())
      .view(gather.getView())
      .applicantLimit(gather.getApplicantLimit())
      .applicantCount(gather.getApplicantCount())
      .commentCount(gather.getCommentCount())
      .author(SimpleUserInfoDto.of(gather.getUser()))
      .build();
  }

  public static CursorPageResponse<SimpleGatherInfoDto> createPage(List<Gather> gathers){
    List<SimpleGatherInfoDto> values = gathers.stream()
      .map(gather -> SimpleGatherInfoDto.of(gather))
      .collect(Collectors.toList());
    Long nextLastId = values.size() == 0 ? null : values.get(values.size() - 1).getGatherId();
    return new CursorPageResponse<>(values, nextLastId);
  }
}
