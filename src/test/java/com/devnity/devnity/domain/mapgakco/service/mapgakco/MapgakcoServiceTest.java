package com.devnity.devnity.domain.mapgakco.service.mapgakco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoFacadeService;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MapgakcoServiceTest {

  @InjectMocks
  private MapgakcoService mapgakcoService;
  @Mock
  private MapgakcoConverter mapgakcoConverter;
  @Mock
  private MapgakcoRepository mapgakcoRepository;
  @Mock
  private MapgakcoFacadeService mapgakcoFacadeService;

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
      .email("email@gmail.com")
      .build();

    mapgakco = Mapgakco.builder()
      .title("맵각코")
      .applicantLimit(5)
      .deadline(LocalDateTime.now())
      .content("모각코 모집중")
      .location("어대역 5번출구")
      .latitude(12.5)
      .longitude(12.5)
      .meetingAt(LocalDateTime.now())
      .user(user)
      .build();
  }

  @Test
  @DisplayName("맵각코를 등록할 수 있다.")
  public void shouldHaveCreateMapgakco() {
    // given
    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
      .title("맵각코")
      .applicantLimit(5)
      .deadline(LocalDateTime.now())
      .content("모각코 모집중")
      .location("어대역 5번출구")
      .latitude(12.5)
      .longitude(12.5)
      .meetingAt(LocalDateTime.now())
      .build();

    given(mapgakcoFacadeService.findUserById(any())).willReturn(user);
    given(mapgakcoConverter.toMapgakco(user, request)).willReturn(mapgakco);
    given(mapgakcoRepository.save(mapgakco)).willReturn(mapgakco);

    // when
    mapgakcoService.create(any(), request);

    // then
    then(mapgakcoFacadeService).should().findUserById(any());
    then(mapgakcoConverter).should().toMapgakco(user, request);
    then(mapgakcoRepository).should().save(mapgakco);
  }

  @Test
  @DisplayName("맵각코를 삭제할 수 있다.")
  public void shouldHaveDeleteMapgakco() {
    // given
    assertEquals(mapgakco.getStatus(), MapgakcoStatus.GATHERING);
    given(mapgakcoFacadeService.findMapgakcoById(any())).willReturn(mapgakco);

    // when
    mapgakcoService.delete(any());

    // then
    then(mapgakcoFacadeService).should().findMapgakcoById(any());
    assertEquals(mapgakco.getStatus(), MapgakcoStatus.DELETED);
  }

}