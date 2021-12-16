package com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
public class MapgakcoApplicantRepositoryImplTest {

  @Autowired
  MapgakcoApplicantRepository applicantRepository;
  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  UserRepository userRepository;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  EntityManager em;

  private Long mapgakcoId;
  private Long userId;

  @BeforeEach
  public void setUp() {
    Mapgakco mapgakco = mapgakcoProvider.createMapgakco(
      userProvider.createUser("1", 1, "1@mail.com"));
    User user = userProvider.createUser();
    mapgakcoProvider.createApplicant(userProvider.createUser("2", 2, "2@mail.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("3", 3, "3@mail.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("4", 4, "4@mail.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("5", 5, "5@mail.com"), mapgakco);
    mapgakcoProvider.createApplicant(userProvider.createUser("6", 6, "6@mail.com"), mapgakco);

    mapgakcoId = mapgakco.getId();
    userId = user.getId();

    em.flush();
    em.clear();
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @PersistenceUnit
  EntityManagerFactory emf;

  @Test
  @DisplayName("findByMapgakcoWithUser가 잘 동작한다.")
  public void findByMapgakcoWithUserTest() {
    // given
    Mapgakco mapgakco = mapgakcoRepository.getById(mapgakcoId);

    // when
    log.info("========query start========");
    List<MapgakcoApplicant> applicants = applicantRepository.getByMapgakcoWithUser(mapgakco);
    for (MapgakcoApplicant applicant : applicants) {
      String name = applicant.getUser().getName();
    }
    log.info("=========query end=========");

    // then
    assertEquals(applicants.size(), 5);
    for (MapgakcoApplicant applicant : applicants) {
      assertFalse(emf.getPersistenceUnitUtil().isLoaded(applicant.getMapgakco()));
      assertTrue(emf.getPersistenceUnitUtil().isLoaded(applicant.getUser()));
    }
  }

  @Test
  @DisplayName("User Lazy확인용 임시 테스트")
  public void userTest() {
    log.info("========query start========");
    User user = userRepository.getById(userId);
    String name = user.getName();
    log.info("=========query end=========");
  }

  @Test
  @DisplayName("Mapgakco Lazy확인용 임시 테스트")
  public void mapgakcoTest() {
    log.info("========query start========");
    Mapgakco mapgakco = mapgakcoRepository.getById(mapgakcoId);
    MapgakcoStatus status = mapgakco.getStatus();
    log.info("=========query end=========");
  }

}
