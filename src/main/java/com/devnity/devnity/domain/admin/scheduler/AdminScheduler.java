package com.devnity.devnity.domain.admin.scheduler;

import com.devnity.devnity.domain.admin.service.AdminInvitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AdminScheduler {

  private final AdminInvitationService invitationService;

  // 매일 자정(00:00:00) 만료된 링크 삭제
  @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
  public void deleteExpiredInvitaion() {
    log.info("==================== 삭제된 초대 링크 리스트 ===================");
    invitationService.deleteExpiredInvitation()
      .forEach(invitation -> log.info("uuid : {}", invitation.getUuid()));
    log.info("========================================================");
  }
}
