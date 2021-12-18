package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.config.TestConfig;
import com.devnity.devnity.setting.provider.UserProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

@Import({TestConfig.class})
@DataJpaTest
public class TransactionTest {

  @Autowired private IntroductionRepository introductionRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private UserProvider userProvider;
  @Autowired private TestEntityManager em;

  private User user;
  private Introduction introduction;

  @BeforeEach
  void setUp() {
    user = userProvider.createUser("hello@gmail.com");
    introduction = user.getIntroduction();
    em.clear();
  }

  @Test
  public void ONE_TO_ONE_LAZY_TEST_USER() throws Exception {
    // given
    Introduction introduction = introductionRepository.findById(this.introduction.getId()).get();
    IntroDto dto = IntroDto.of(introduction);
    // when

    // then
//    Assertions.assertThat(dto.getUserEmail()).isEqualTo(user.getEmail());
  }

  @Test
  public void ONE_TO_ONE_LAZY_TEST_INTRODUCTION() throws Exception {
    // given
    userRepository.findById(user.getId());

    // when

    // then
  }


  
}
