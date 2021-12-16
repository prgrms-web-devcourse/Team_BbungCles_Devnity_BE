package com.devnity.devnity.domain.gather.utils.event;

public class DeleteGatherApplicationEvent extends GatherEvent{

  public DeleteGatherApplicationEvent(Long gatherId) {
    super(gatherId);
  }
}
