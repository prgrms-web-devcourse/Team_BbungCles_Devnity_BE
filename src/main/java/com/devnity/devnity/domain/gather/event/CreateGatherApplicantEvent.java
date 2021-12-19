package com.devnity.devnity.domain.gather.event;

public class CreateGatherApplicantEvent extends SimpleGatherEvent {

  public CreateGatherApplicantEvent(Long gatherId) {
    super(gatherId);
  }
}