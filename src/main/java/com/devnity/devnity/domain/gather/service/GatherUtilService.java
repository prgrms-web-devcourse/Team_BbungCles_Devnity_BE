package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.domain.gather.entity.Gather;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GatherUtilService {

  private final GatherRetrieveService gatherRetrieveService;

  @Transactional
  public List<Gather> closeExpiredGather(){
    List<Gather> expiredGathers = gatherRetrieveService.getExpiredGathers();
    for(Gather gather : expiredGathers){
      gather.close();
    }
    return expiredGathers;
  }

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
