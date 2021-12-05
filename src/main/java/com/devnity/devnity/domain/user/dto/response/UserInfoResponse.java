package com.devnity.devnity.domain.user.dto.response;

import com.devnity.devnity.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserInfoResponse {

  private UserDto user;

  public UserInfoResponse(UserDto user) {
    this.user = user;
  }
}
