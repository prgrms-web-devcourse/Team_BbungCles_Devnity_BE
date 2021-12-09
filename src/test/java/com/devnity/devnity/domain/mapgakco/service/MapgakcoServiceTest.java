package com.devnity.devnity.domain.mapgakco.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Group;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.GroupRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class MapgakcoServiceTest {

  @Autowired
  private MapgakcoService mapgakcoService;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private GroupRepository groupRepository;
  @Autowired
  private GenerationRepository generationRepository;
  @Autowired
  private CourseRepository courseRepository;

  private Long userId;

  @BeforeEach
  public void setUp() {
    Group group = new Group("C");
    groupRepository.save(group);

    Generation generation = new Generation(1);
    generationRepository.save(generation);

    Course course = new Course("백엔드");
    courseRepository.save(course);

    User user = User.builder()
      .name("전찬의")
      .email("email@gmail.com")
      .password("123")
      .role(UserRole.STUDENT)
      .group(group)
      .generation(generation)
      .course(course)
      .build();

    userId = userRepository.save(user).getId();
  }

  @Test
  @DisplayName("맵각코를 등록할 수 있다.")
  public void createMapgakco() {
    User user = userRepository.getById(userId);

    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
      .title("맵각코")
      .applicantLimit(5)
      .deadline(LocalDateTime.now())
      .content("모각코 모집중")
      .location("어대역 5번출구")
      .latitude(12.5)
      .longitude(12.5)
      .meetingDateTime(LocalDateTime.now())
      .build();

    MapgakcoStatus mapgakcoStatus = mapgakcoService.create(userId, request);

    assertThat(mapgakcoStatus).isEqualTo(MapgakcoStatus.GATHERING);
  }

}