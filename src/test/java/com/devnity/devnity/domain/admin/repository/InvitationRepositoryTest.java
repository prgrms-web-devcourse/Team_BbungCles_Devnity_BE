package com.devnity.devnity.domain.admin.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.setting.config.TestConfig;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@DataJpaTest
class InvitationRepositoryTest {

  @Autowired
  InvitationRepository invitationRepository;

  @Disabled
  @Test
  void 만료된_링크_조회_테스트() throws InterruptedException {
    // Given
    invitationRepository.save(
      Invitation.builder()
        .course("BE")
        .generation(1)
        .role(UserRole.STUDENT)
        .deadline(LocalDate.now())
        .build()
    );
    invitationRepository.save(
      Invitation.builder()
        .course("BE")
        .generation(1)
        .role(UserRole.STUDENT)
        .deadline(LocalDate.now().minusDays(1))
        .build()
    );
    Thread.sleep(3 * 1000);

    // When
    List<Invitation> expired = invitationRepository.findExpiredInvitation();

    // Then
    assertThat(expired, hasSize(1));
  }

}