package com.devnity.devnity.domain.user.dto.response;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoResponse {

  private UserDto user;
  private IntroductionDto introduction;

  public UserInfoResponse(UserDto user,
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
