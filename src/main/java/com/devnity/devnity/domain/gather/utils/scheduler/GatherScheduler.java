package com.devnity.devnity.domain.gather.utils.scheduler;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.service.GatherUtilService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class GatherScheduler {

  private final GatherUtilService gatherUtilService;

  // 매일 자정 마감된 모집 상태 CLOSED
  @Scheduled(cron = "0 0 * * * ?", zone = "Asia/Seoul")
  public void closeExpiredGather() {
    log.info("==================== 마감된 모집 리스트 ===================");
    gatherUtilService.closeExpiredGather()
      .forEach(gather -> log.info("id : {}, 제목 : {}", gather.getId(), gather.getTitle()));
    log.info("========================================================");
  }

}
