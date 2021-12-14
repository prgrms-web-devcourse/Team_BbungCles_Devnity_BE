package com.devnity.devnity.domain.gather.dto;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherSimpleInfoDto {

  private Long gatherId;

  private String status;

  private String title;

  private GatherCategory category;

  private LocalDateTime deadline;

  private LocalDateTime createdAt;

  private Integer applicantLimit;

  private int view;

  private int applicantCount;

  private int commentCount;

  private SimpleUserInfoDto simpleUserInfo;

  public static GatherSimpleInfoDto of(Gather gather) {
    return GatherSimpleInfoDto.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus().getStatus())   // FIXME : GatherStatus Json 파싱 해결하기
      .title(gather.getTitle())
      .category(gather.getCategory())
      .deadline(gather.getDeadline())
      .createdAt(gather.getCreatedAt())
      .view(gather.getView())
      .applicantLimit(gather.getApplicantLimit())
      .applicantCount(gather.getApplicants().size())  // FIXME : event 리스너 구현 후 컬럼으로 변경
      .commentCount(gather.getComments().size())      // FIXME : event 리스너 구현 후 컬럼으로 변경
      .simpleUserInfo(SimpleUserInfoDto.of(
        gather.getUser(),
        gather.getUser().getIntroduction().getProfileImgUrl()
      ))
      .build();
  }
}
