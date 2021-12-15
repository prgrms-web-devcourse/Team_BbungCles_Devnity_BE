package com.devnity.devnity.domain.gather.event;

public class DeleteGatherApplicationEvent extends GatherEvent{

  public DeleteGatherApplicationEvent(Long gatherId) {
    super(gatherId);
  }
}
