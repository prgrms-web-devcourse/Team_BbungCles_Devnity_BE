package com.devnity.devnity.domain.gather.utils.event;

public class DeleteGatherCommentEvent extends GatherEvent{

  public DeleteGatherCommentEvent(Long gatherId) {
    super(gatherId);
  }
}
