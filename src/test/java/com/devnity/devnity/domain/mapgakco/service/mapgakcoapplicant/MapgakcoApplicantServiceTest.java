package com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoApplicantConverter;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
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
class MapgakcoApplicantServiceTest {

  @InjectMocks
  private MapgakcoApplicantService mapgakcoApplicantService;
  @Mock
  private MapgakcoApplicantConverter mapgakcoApplicantConverter;
  @Mock
  private MapgakcoApplicantRepository mapgakcoApplicantRepository;
  @Mock
  private MapgakcoRetrieveService mapgakcoRetrieveService;

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
      .applicantLimit(2)
      .content("모각코 모집중")
      .location("어대역 5번출구")
      .latitude(12.5)
      .longitude(12.5)
      .meetingAt(LocalDateTime.now())
      .user(user)
      .build();
  }

  @Test
  @DisplayName("맵각코를 신청할 수 있다.")
  public void shouldHaveApplyMapgakco() {
    // given
    MapgakcoApplicant applicant = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();

    assertEquals(1, mapgakco.getApplicantCount());
    assertEquals(2, mapgakco.getApplicantLimit());
    assertEquals(MapgakcoStatus.GATHERING, mapgakco.getStatus());

    given(mapgakcoRetrieveService.getMapgakcoById(anyLong())).willReturn(mapgakco);
    given(mapgakcoRetrieveService.getUserById(anyLong())).willReturn(user);
    given(mapgakcoApplicantConverter.toApplicant(mapgakco, user)).willReturn(applicant);

    // when
    mapgakcoApplicantService.applyForMapgakco(1L, 2L);

    // then
    then(mapgakcoRetrieveService).should().getMapgakcoById(anyLong());
    then(mapgakcoRetrieveService).should().getUserById(anyLong());
    then(mapgakcoApplicantConverter).should().toApplicant(mapgakco, user);
    then(mapgakcoApplicantRepository).should().save(applicant);

    assertEquals(2, mapgakco.getApplicantCount());
    assertEquals(MapgakcoStatus.FULL, mapgakco.getStatus());
  }

  @Test
  @DisplayName("맵각코를 신청 취소할 수 있다.")
  public void shouldHaveCancelMapgakco() {
    // given
    MapgakcoApplicant applicant = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();

    assertEquals(2, mapgakco.getApplicantLimit());

    given(mapgakcoRetrieveService.getMapgakcoById(anyLong())).willReturn(mapgakco);
    given(mapgakcoRetrieveService.getUserById(anyLong())).willReturn(user);
    given(mapgakcoApplicantConverter.toApplicant(mapgakco, user)).willReturn(applicant);
    mapgakcoApplicantService.applyForMapgakco(1L, 2L);

    assertEquals(2, mapgakco.getApplicantCount());
    assertEquals(MapgakcoStatus.FULL, mapgakco.getStatus());

    // when
    mapgakcoApplicantService.cancelForMapgakco(1L, 2L);

    // then
    then(mapgakcoRetrieveService).should(times(2)).getMapgakcoById(anyLong());
    then(mapgakcoRetrieveService).should(times(2)).getUserById(anyLong());
    then(mapgakcoApplicantConverter).should().toApplicant(mapgakco, user);
    then(mapgakcoApplicantRepository).should().save(applicant);
    then(mapgakcoApplicantRepository).should().deleteByMapgakcoAndUser(mapgakco, user);

    assertEquals(1, mapgakco.getApplicantCount());
    assertEquals(MapgakcoStatus.GATHERING, mapgakco.getStatus());
  }


}