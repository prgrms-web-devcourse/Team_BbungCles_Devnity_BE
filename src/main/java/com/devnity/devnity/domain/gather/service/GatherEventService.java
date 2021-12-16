package com.devnity.devnity.domain.gather.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GatherEventService {

  private final GatherRetrieveService gatherRetrieveService;

  @Transactional
  public void increaseCommentCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .increaseCommentCount();
  }

  @Transactional
  public void decreaseCommentCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .decreaseCommentCount();
  }

  @Transactional
  public void increaseApplicantCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .increaseApplicantCount();
  }

  @Transactional
  public void decreaseApplicantCount(Long gatherId){
    gatherRetrieveService.getGather(gatherId)
      .decreaseApplicantCount();
  }
}
