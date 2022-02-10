package com.devnity.devnity.domain.gather.event;

import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateGatherEvent {

  private SimpleUserInfoDto simpleUserInfoDto;
  private SimpleGatherInfoDto simpleGatherInfoDto;

}
