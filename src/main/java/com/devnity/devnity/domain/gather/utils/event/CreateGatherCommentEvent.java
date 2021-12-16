package com.devnity.devnity.domain.gather.utils.event;

public class CreateGatherCommentEvent extends GatherEvent {

  public CreateGatherCommentEvent(Long gatherId) {
    super(gatherId);
  }
}
