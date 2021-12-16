package com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MapgakcoApplicantRepositoryTest {

  @Autowired
  MapgakcoApplicantRepository applicantRepository;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  EntityManager em;

  private Mapgakco mapgakco;
  private Mapgakco mapgakco2;
  private User user;
  private MapgakcoApplicant applicant;

  @BeforeEach
  public void setUp() {
    user = userProvider.createUser("1", 1, "1@email.com");
    mapgakco = mapgakcoProvider.createMapgakco(user);
    applicant = mapgakcoProvider.createApplicant(user, mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("2", 2, "2@email.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("3", 3, "3@email.com"), mapgakco);

    User user2 = userProvider.createUser("4", 4, "4@email.com");
    mapgakco2 = mapgakcoProvider.createMapgakco(user2);
    mapgakcoProvider.createApplicant(user2, mapgakco2);
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @DisplayName("쿼리 메소드 findByMapgakcoAndUser와 deleteByMapgakcoAndUser가 잘 동작한다.")
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

  @Test
  @DisplayName("쿼리 메소드 getByMapgakco가 잘 동작한다.")
  public void getByMapgakcoTest() {
    List<MapgakcoApplicant> applicants = applicantRepository.getByMapgakco(mapgakco);
    assertEquals(applicants.size(), 3);

    List<MapgakcoApplicant> applicants2 = applicantRepository.getByMapgakco(mapgakco2);
    assertEquals(applicants2.size(), 1);

  }

}