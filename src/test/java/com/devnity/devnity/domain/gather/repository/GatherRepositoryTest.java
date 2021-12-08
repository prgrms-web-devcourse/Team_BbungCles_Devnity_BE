package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.EntityProvider;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

//@ExtendWith(SpringExtension.class)  // test app-context를 junit에 포함시킴
//@DataJpaTest  // Jpa 관련 설정만 불러옴
@SpringBootTest
class GatherRepositoryTest {

  @Autowired
  UserRepository userRepository;
  @Autowired
  GatherRepository gatherRepository;
  @Autowired
  GatherCommentRepository commentRepository;
  @Autowired
  GatherApplicantRepository applicantRepository;

//  @Transactional
  @Test
  public void 양방향매핑_테스트() {
    User user = userRepository.findById(3L).get();  // data.sql에서 정의된 user

    Gather gather = gatherRepository.save(EntityProvider.createGather(user));
    gatherRepository.flush();

    GatherComment parentComment = commentRepository.save(EntityProvider.createGatherComment(gather, user));
    commentRepository.flush();

    GatherComment childComment = commentRepository.save(EntityProvider.createGatherCommentChild(gather, parentComment, user));
    commentRepository.flush();

    GatherApplicant applicant = applicantRepository.save(EntityProvider.createGatherApplicant(gather, user));
    applicantRepository.flush();

    // ------------------------------------------------

//    Gather result = gatherRepository.findById(gather.getId()).get();
//
//    // FIXME : 로그로 변경
//    System.out.println(result);
  }

}