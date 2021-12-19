package com.devnity.devnity.domain.gather.event;

public class DeleteGatherApplicationEvent extends SimpleGatherEvent {

  public DeleteGatherApplicationEvent(Long gatherId) {
    super(gatherId);
  }
}
