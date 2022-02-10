package com.devnity.devnity.domain.gather.event;

import com.devnity.devnity.web.gather.service.GatherUtilService;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GatherEventHandler {

  private final GatherUtilService gatherUtilService;

  @Async
  @EventListener
  public void increaseCommentCount(CreateGatherCommentEvent event) {
    gatherUtilService.increaseCommentCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void decreaseCommentCount(DeleteGatherCommentEvent event) {
    gatherUtilService.decreaseCommentCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void increaseApplicantCount(CreateGatherApplicantEvent event) {
    gatherUtilService.increaseApplicantCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void decreaseApplicantCount(DeleteGatherApplicationEvent event) {
    gatherUtilService.decreaseApplicantCount(event.getGatherId());
  }

  @Async
  @EventListener
  public void sendAlarmToSlack(CreateGatherEvent event) {
    gatherUtilService.sendAlarmToSlack(event.getSimpleUserInfoDto(), event.getSimpleGatherInfoDto());
  }

}
