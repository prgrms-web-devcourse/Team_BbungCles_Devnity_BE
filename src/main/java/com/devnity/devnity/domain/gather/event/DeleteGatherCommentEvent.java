package com.devnity.devnity.domain.gather.event;

public class DeleteGatherCommentEvent extends GatherEvent{

  public DeleteGatherCommentEvent(Long gatherId) {
    super(gatherId);
  }
}
