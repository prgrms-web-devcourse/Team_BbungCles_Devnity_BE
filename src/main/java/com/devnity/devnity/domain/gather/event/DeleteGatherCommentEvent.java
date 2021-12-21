package com.devnity.devnity.domain.gather.event;

public class DeleteGatherCommentEvent extends SimpleGatherEvent {

  public DeleteGatherCommentEvent(Long gatherId) {
    super(gatherId);
  }
}
