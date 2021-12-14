package com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MapgakcoApplicantQueryMethodTest {

  @Autowired
  MapgakcoApplicantRepository applicantRepository;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;

  private Mapgakco mapgakco;
  private User user;
  private MapgakcoApplicant applicant;

  @BeforeEach
  public void setUp() {
    user = userProvider.createUser();
    mapgakco = mapgakcoProvider.createMapgakco(user);
    applicant = mapgakcoProvider.createApplicant(user, mapgakco);
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @DisplayName("MapgakcoApplicantRepository의 쿼리메소드들이 잘 작동한다.")
  public void queryMethodTest() {
    // given
    Optional<MapgakcoApplicant> found1 = applicantRepository.findByMapgakcoAndUser(mapgakco, user);
    assertNotEquals(found1, Optional.empty());
    assertEquals(found1.get(), applicant);

    // when
    applicantRepository.deleteByMapgakcoAndUser(mapgakco, user);

    // then
    Optional<MapgakcoApplicant> found2 = applicantRepository.findByMapgakcoAndUser(mapgakco, user);
    assertEquals(found2, Optional.empty());

  }


}
