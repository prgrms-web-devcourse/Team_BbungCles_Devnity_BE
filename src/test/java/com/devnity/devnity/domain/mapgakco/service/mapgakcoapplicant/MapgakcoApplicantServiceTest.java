package com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoApplicantConverter;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
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
class MapgakcoApplicantServiceTest {

  @InjectMocks
  private MapgakcoApplicantService mapgakcoApplicantService;
  @Mock
  private MapgakcoApplicantConverter mapgakcoApplicantConverter;
  @Mock
  private MapgakcoApplicantRepository mapgakcoApplicantRepository;
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
      .applicantLimit(2)
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
  @DisplayName("맵각코를 신청할 수 있다.")
  public void shouldHaveApplyMapgakco() {
    // given
    MapgakcoApplicant applicant = MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();

    assertEquals(1, mapgakco.getApplicantNumber());
    assertEquals(2, mapgakco.getApplicantLimit());

    given(mapgakcoFacadeService.findMapgakcoById(anyLong())).willReturn(mapgakco);
    given(mapgakcoFacadeService.findUserById(anyLong())).willReturn(user);
    given(mapgakcoApplicantConverter.toApplicant(mapgakco, user)).willReturn(applicant);

    // when
    mapgakcoApplicantService.applyForMapgakco(1L, 2L);

    // then
    then(mapgakcoFacadeService).should().findMapgakcoById(anyLong());
    then(mapgakcoFacadeService).should().findUserById(anyLong());
    then(mapgakcoApplicantConverter).should().toApplicant(mapgakco, user);
    then(mapgakcoApplicantRepository).should().save(applicant);

    assertEquals(2, mapgakco.getApplicantNumber());
    assertEquals(MapgakcoStatus.FULL, mapgakco.getStatus());
  }


}