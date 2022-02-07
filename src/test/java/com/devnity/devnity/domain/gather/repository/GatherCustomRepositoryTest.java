package com.devnity.devnity.domain.gather.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@DataJpaTest
class GatherCustomRepositoryTest {

  /***
   *   List<Gather> findGathersHostedBy(User host);
   *   List<Gather> findGathersAppliedBy(User applicant);
   */

  @Autowired private GatherRepository gatherRepository;

  @Autowired private UserProvider userProvider;

  @Autowired private GatherProvider gatherProvider;

  @DisplayName("findGathersHostedBy() 테스트")
  @Test
  public void testFindGathersHostedBy() throws Exception {
    // given
    User user = userProvider.createUser();

    int size = 5;
    for (int i = 0; i < size; i++) {
      gatherProvider.createGather(user);
    }

    gatherProvider.createGather(user, GatherStatus.DELETED);

    // when
    List<Gather> results = gatherRepository.findGathersHostedBy(user);

    // then
    assertThat(results).hasSize(size);
  }

  @DisplayName("findGathersAppliedBy() 테스트")
  @Test
  public void testFindGathersAppliedBy() throws Exception {
    // given
    User host = userProvider.createUser("host@gmail.com");
    User applicant = userProvider.createUser("applicant@gmail.com");

    int size = 5;
    for (int i = 0; i < 5; i++) {
      Gather gather = gatherProvider.createGather(host);
      GatherApplicant ga = GatherApplicant.of(applicant, gather);
      gather.addApplicant(ga);
    }

    gatherProvider.createGather(host, GatherStatus.DELETED);

    // when
    List<Gather> result1 = gatherRepository.findGathersAppliedBy(applicant);
    List<Gather> result2 = gatherRepository.findGathersAppliedBy(host);

    // then
    assertThat(result1).hasSize(size);
    assertThat(result2).isEmpty();
  }


}
