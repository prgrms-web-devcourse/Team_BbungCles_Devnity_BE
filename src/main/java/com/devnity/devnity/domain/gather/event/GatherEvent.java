package com.devnity.devnity.domain.gather.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class GatherEvent {

  private Long gatherId;

}
