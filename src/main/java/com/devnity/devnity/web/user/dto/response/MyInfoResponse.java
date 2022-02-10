package com.devnity.devnity.web.user.dto.response;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.web.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MyInfoResponse {

  private UserDto user;
  private IntroductionDto introduction;

  public MyInfoResponse(UserDto user,
      IntroductionDto introduction) {
    this.user = user;
    this.introduction = introduction;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("UserInfoResponse{");
    sb.append("user=").append(user);
    sb.append(", introduction=").append(introduction);
    sb.append('}');
    return sb.toString();
  }
}
