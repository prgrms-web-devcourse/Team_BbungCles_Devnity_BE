package com.devnity.devnity.domain.introduction.dto.response;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.web.user.dto.UserDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserIntroductionResponse {

  private UserDto user;
  private IntroductionDto introduction;

  private UserIntroductionResponse(UserDto user,
    IntroductionDto introduction) {
    this.user = user;
    this.introduction = introduction;
  }

  public static UserIntroductionResponse of(UserDto user, IntroductionDto introduction) {

    return new UserIntroductionResponse(user, introduction);
  }

}
