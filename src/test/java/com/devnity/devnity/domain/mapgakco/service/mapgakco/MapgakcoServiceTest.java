package com.devnity.devnity.domain.mapgakco.service.mapgakco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willAnswer;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.web.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.time.LocalDateTime;

import com.devnity.devnity.web.mapgakco.service.mapgakco.MapgakcoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class MapgakcoServiceTest {

  @InjectMocks
  private MapgakcoService mapgakcoService;
  @Mock
  private MapgakcoConverter mapgakcoConverter;
  @Mock
  private MapgakcoRepository mapgakcoRepository;
  @Mock
  private MapgakcoRetrieveService mapgakcoRetrieveService;
  @Mock
  private UserRepository userRepository;

  private User user;
  private Mapgakco mapgakco;

  @BeforeEach
  public void setUp() {

    Generation generation = new Generation(1);
    Course course = new Course("FE");
    user = User.builder()
      .course(course)
      .generation(generation)
      .name("name")
      .password("password")
      .role(UserRole.STUDENT)
      .email("email@naver.com")
      .build();

    mapgakco = Mapgakco.builder()
      .title("????????? ??????")
      .applicantLimit(5)
      .content("????????? ??????")
      .location("????????? ??????")
      .latitude(33.450701)
      .longitude(126.570667)
      .meetingAt(LocalDateTime.now())
      .user(user)
      .build();
  }

  @Test
  @DisplayName("???????????? ????????? ??? ??????.")
  public void shouldHaveCreateMapgakco() {
    // given
    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
      .title("????????? ??????")
      .applicantLimit(5)
      .content("????????? ??????")
      .location("????????? ??????")
      .latitude(33.450701)
      .longitude(126.570667)
      .meetingAt(LocalDateTime.now())
      .build();

    given(mapgakcoRetrieveService.getUserById(any())).willReturn(user);
    given(mapgakcoConverter.toMapgakco(user, request)).willReturn(mapgakco);
    given(mapgakcoRepository.save(mapgakco)).willReturn(mapgakco);

    // when
    mapgakcoService.create(any(), request);

    // then
    then(mapgakcoRetrieveService).should().getUserById(any());
    then(mapgakcoConverter).should().toMapgakco(user, request);
    then(mapgakcoRepository).should().save(mapgakco);
  }

  @Test
  @DisplayName("???????????? ????????? ??? ??????.")
  public void shouldHaveDeleteMapgakco() {
    // given
    assertEquals(mapgakco.getStatus(), MapgakcoStatus.GATHERING);

    willAnswer(invocation -> {
      ReflectionTestUtils.setField((User) invocation.getArgument(0), "id", 3L);
      return null;
    }).given(userRepository).save(this.user);
    userRepository.save(user);

    given(mapgakcoRetrieveService.getMapgakcoById(any())).willReturn(mapgakco);

    // when
    mapgakcoService.deleteMapgakco(3L, any());

    // then
    then(mapgakcoRetrieveService).should().getMapgakcoById(any());
    assertEquals(mapgakco.getStatus(), MapgakcoStatus.DELETED);
  }

}