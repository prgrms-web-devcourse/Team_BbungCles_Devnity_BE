package com.devnity.devnity.domain.gather.dto.response;

import com.devnity.devnity.domain.gather.dto.GatherDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GatherCardResponse {

  private Long gatherId;

  private GatherStatus status;

  private String title;

  private GatherCategory category;

  private int view;

  private LocalDateTime deadline;

  private Integer applicantLimit;

  private int applicantCount;

  private int commentCount;

  private SimpleUserInfoDto simpleUserInfo;

  public static GatherCardResponse of(Gather gather) {
    return GatherCardResponse.builder()
      .gatherId(gather.getId())
      .status(gather.getStatus())
      .title(gather.getTitle())
      .category(gather.getCategory())
      .view(gather.getView())
      .deadline(gather.getDeadline())
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
