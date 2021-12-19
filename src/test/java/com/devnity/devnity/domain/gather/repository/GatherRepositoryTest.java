package com.devnity.devnity.domain.gather.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)  // test app-context를 junit에 포함시킴
@DataJpaTest  // Jpa 관련 설정만 불러옴
@Slf4j
class GatherRepositoryTest {

  @Autowired
  GatherRepository gatherRepository;

  @Autowired
  UserProvider userProvider;
  @Autowired
  GatherProvider gatherProvider;

  @Autowired
  TestEntityManager testEntityManager;

  @Test
  void 양방향매핑_테스트_DataJpaTest() {
    User user = userProvider.createUser();
    Gather gather = gatherProvider.createGather(user);
    GatherComment parentComment = gatherProvider.createParentComment(user, gather);
    gatherProvider.createChildComment(user, gather, parentComment);
    gatherProvider.createApplicant(user, gather);

    // 영속성 컨텍스트에서 조회되지 않도록 컨텍스트는 clear 해준다.
    testEntityManager.clear();

    // ------------------------------------------------

    Gather resultGather = gatherRepository.findById(gather.getId()).get();  // lazy 이므로 gather 안에는 다 proxy 객체임

    List<GatherComment> comments = resultGather.getComments();
    List<GatherApplicant> applicants = resultGather.getApplicants();
    log.info("{}", comments);
    log.info("{}", applicants);

    log.info("{}", comments.get(0).getId());
    log.info("{}", applicants.get(0).getId());
  }

  @Test
  void 페이징_조회_테스트() {
    User user = userProvider.createUser();

    for (int i = 0; i < 10; i++) {
      gatherProvider.createGather(user);
      gatherProvider.createGather(user, GatherStatus.CLOSED);
      gatherProvider.createGather(user, GatherStatus.FULL);
      gatherProvider.createGather(user, GatherStatus.DELETED);
    }

    log.info("=============================================================================");
    List<Gather> paging1 = gatherRepository.findGathersByPaging(null, GatherCategory.STUDY, List.of(GatherStatus.GATHERING),null, 20);
    for(Gather gather : paging1){
      log.info("{}", gather);
    }
    log.info("=============================================================================");
    List<Gather> paging2 = gatherRepository.findGathersByPaging(null, null, List.of(GatherStatus.CLOSED, GatherStatus.FULL),null, 20);
    for(Gather gather : paging2){
      log.info("{}", gather);
    }
  }

  @Disabled
  @Test
  void 만료된_모집_조회_테스트() throws InterruptedException {

    User user = userProvider.createUser();
    for (int i = 0; i < 10; i++) {
      gatherProvider.createGather(user);
      gatherProvider.createGather(user, GatherStatus.CLOSED);
      gatherProvider.createGather(user, GatherStatus.FULL);
      gatherProvider.createGather(user, GatherStatus.DELETED);
    }
    Thread.sleep(3*1000);

    List<Gather> expiredGathers = gatherRepository.findExpiredGathers();
    assertThat(expiredGathers, hasSize(20));
  }


}