package com.devnity.devnity.domain.gather.utils.event;

public class CreateGatherApplicantEvent extends GatherEvent{

  public CreateGatherApplicantEvent(Long gatherId) {
    super(gatherId);
  }
}