package com.devnity.devnity.domain.introduction.dto.response;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.web.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SuggestResponse {

  private UserDto user;
  private IntroductionDto introduction;

  private SuggestResponse(UserDto user,
    IntroductionDto introduction) {
    this.user = user;
    this.introduction = introduction;
  }

  public static SuggestResponse of(UserDto user, IntroductionDto introduction) {
    return new SuggestResponse(user, introduction);
  }
}
