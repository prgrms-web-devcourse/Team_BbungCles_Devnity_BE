package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.GatherProvider;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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

  }


}