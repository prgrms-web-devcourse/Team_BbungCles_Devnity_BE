package com.devnity.devnity.domain.gather.event;

import com.devnity.devnity.domain.gather.service.GatherEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GatherEventHandler {

  private final GatherEventService gatherEventService;

  @Async
  @EventListener
  public void increaseCommentCount(CreateGatherCommentEvent event) {
    gatherEventService.increaseCommentCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void decreaseCommentCount(DeleteGatherCommentEvent event) {
    gatherEventService.decreaseCommentCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void increaseApplicantCount(CreateGatherApplicantEvent event) {
    gatherEventService.increaseApplicantCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void decreaseApplicantCount(DeleteGatherApplicationEvent event) {
    gatherEventService.decreaseApplicantCount(event.getGatherId());
  }

}
