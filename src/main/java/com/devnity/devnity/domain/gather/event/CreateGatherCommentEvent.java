package com.devnity.devnity.domain.gather.event;

public class CreateGatherCommentEvent extends GatherEvent {

  public CreateGatherCommentEvent(Long gatherId) {
    super(gatherId);
  }
}
