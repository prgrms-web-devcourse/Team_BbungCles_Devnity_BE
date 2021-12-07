package com.devnity.devnity.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.devnity.devnity.DevnityApplication;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Group;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

  @Autowired private UserRepository userRepository;

  @DisplayName("이메일로 사용자를 찾을 수 있다")
  @Test
  public void testFindByEmail() throws Exception {
    // given
    String email = "user@gmail.com";

    // when
    Optional<User> actual = userRepository.findUserByEmail(email);

    // then
    assertThat(actual).isPresent();
    assertThat(actual.get().getEmail()).isEqualTo(email);
  }

  @DisplayName("이메일 중복을 확인할 수 있다")
  @Test
  public void testExistsByEmail() throws Exception {
    // given
    String email = "user@gmail.com";

    // when
    boolean result = userRepository.existsByEmail(email);

    // then
    assertThat(result).isTrue();
  }
}