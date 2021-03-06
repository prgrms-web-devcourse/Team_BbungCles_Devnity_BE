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

import com.devnity.devnity.web.mapgakco.service.mapgakcoapplicant.MapgakcoApplicantService;
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
      .title("????????? ??????")
      .applicantLimit(1)
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
  public void shouldHaveApplyMapgakco() {
    // given
    MapgakcoApplicant applicant = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();

    assertEquals(0, mapgakco.getApplicantCount());
    assertEquals(1, mapgakco.getApplicantLimit());
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

    assertEquals(1, mapgakco.getApplicantCount());
    assertEquals(MapgakcoStatus.FULL, mapgakco.getStatus());
  }

  @Test
  @DisplayName("???????????? ?????? ????????? ??? ??????.")
  public void shouldHaveCancelMapgakco() {
    // given
    MapgakcoApplicant applicant = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();

    assertEquals(1, mapgakco.getApplicantLimit());

    given(mapgakcoRetrieveService.getMapgakcoById(anyLong())).willReturn(mapgakco);
    given(mapgakcoRetrieveService.getUserById(anyLong())).willReturn(user);
    given(mapgakcoApplicantConverter.toApplicant(mapgakco, user)).willReturn(applicant);
    mapgakcoApplicantService.applyForMapgakco(1L, 2L);

    assertEquals(1, mapgakco.getApplicantCount());
    assertEquals(MapgakcoStatus.FULL, mapgakco.getStatus());

    // when
    mapgakcoApplicantService.cancelForMapgakco(1L, 2L);

    // then
    then(mapgakcoRetrieveService).should(times(2)).getMapgakcoById(anyLong());
    then(mapgakcoRetrieveService).should(times(2)).getUserById(anyLong());
    then(mapgakcoApplicantConverter).should().toApplicant(mapgakco, user);
    then(mapgakcoApplicantRepository).should().save(applicant);
    then(mapgakcoRetrieveService).should().getApplicantByMapgakcoAndUser(mapgakco, user);

    assertEquals(0, mapgakco.getApplicantCount());
    assertEquals(MapgakcoStatus.GATHERING, mapgakco.getStatus());
  }


}