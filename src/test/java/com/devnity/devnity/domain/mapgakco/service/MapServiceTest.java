package com.devnity.devnity.domain.mapgakco.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.setting.provider.MapgakcoProvider;
import com.devnity.devnity.setting.provider.TestHelper;
import com.devnity.devnity.setting.provider.UserProvider;
import com.devnity.devnity.web.mapgakco.service.MapService;
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
class MapServiceTest {

  @Autowired
  MapgakcoProvider mapgakcoProvider;
  @Autowired
  UserProvider userProvider;
  @Autowired
  TestHelper testHelper;
  @Autowired
  MapgakcoRepository mapgakcoRepository;
  @Autowired
  MapService mapService;

  @BeforeEach
  @Transactional
  void setUp() throws Exception {
    User user = userProvider.createUser();
    mapgakcoProvider.createMapgakco(user, 33.450701, 126.570667);
    for (int i = 0; i < 5; i++) {
      Double latitude = 33.450701 + (Math.random() / 10.0) - 0.05;
      Double longitude = 126.570667 + (Math.random() / 10) - 0.05;
      mapgakcoProvider.createMapgakco(user, latitude, longitude);
    }
  }

  @AfterEach
  void tearDown() {
    testHelper.clean();
  }

  @Test
  @DisplayName("거리계산이 잘 된다.")
  void distanceTest() {
    // 서울시청(37.566752, 126.978935) ~ 구리시청(37.594072, 127.130024) : 약13665m
    Double result = mapService.distance(37.566752, 126.978935, 37.594072, 127.130024, "meter");
    log.info("meter : {}", result);
    assertTrue(result > 13000.0);
  }

  @Test
  @DisplayName("최대거리 계산이 잘 된다.")
  void maxDistanceByTwoPointTest() {
    Double result = mapService.maxDistanceByTwoPoint(
      37.566752, 126.978935,
      37.594072, 127.130024, 37.554072, 126.968935, "meter");
    log.info("meter : {}", result);
    assertTrue(result > 13000.0);
  }

}