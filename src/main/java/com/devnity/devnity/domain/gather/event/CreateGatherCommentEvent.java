package com.devnity.devnity.domain.gather.event;

public class CreateGatherCommentEvent extends SimpleGatherEvent {

  public CreateGatherCommentEvent(Long gatherId) {
    super(gatherId);
  }
}
