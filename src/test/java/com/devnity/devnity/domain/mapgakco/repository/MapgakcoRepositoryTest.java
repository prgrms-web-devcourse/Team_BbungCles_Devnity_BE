package com.devnity.devnity.domain.mapgakco.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
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
class MapgakcoRepositoryTest {

  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  EntityManager em;

  private Long mapgakcoId;

  @BeforeEach
  public void setUp() {
    User user = userProvider.createUser();
    mapgakcoId = mapgakcoProvider.createMapgakco(user).getId();
    mapgakcoProvider.createMapgakco(user);
    mapgakcoProvider.createMapgakco(user);
    em.flush();
    em.clear();
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @DisplayName("쿼리메소드 findByIdAndStatusNot가 잘 동작한다.")
  void findByIdAndStatusNotTest() {
    Optional<Mapgakco> foundMapgakco = mapgakcoRepository.findByIdAndStatusNot(mapgakcoId,
      MapgakcoStatus.DELETED);
    assertNotEquals(foundMapgakco, Optional.empty());

    Mapgakco mapgakco = foundMapgakco.get();
    mapgakco.delete();
    em.flush();
    em.clear();

    Optional<Mapgakco> foundMapgakco2 = mapgakcoRepository.findByIdAndStatusNot(mapgakcoId,
      MapgakcoStatus.DELETED);
    assertEquals(foundMapgakco2, Optional.empty());
  }

  @Test
  @DisplayName("쿼리메소드 findByStatusNot가 잘 동작한다.")
  void findByStatusNotTest() {
    List<Mapgakco> mapgakcos = mapgakcoRepository.getByStatusNot(MapgakcoStatus.DELETED);
    assertEquals(mapgakcos.size(), 3);

    for (Mapgakco m : mapgakcos) {
      m.delete();
    }
    em.flush();
    em.clear();

    List<Mapgakco> mapgakcos2 = mapgakcoRepository.getByStatusNot(MapgakcoStatus.DELETED);
    assertEquals(mapgakcos2.size(), 0);
  }


}