package com.devnity.devnity.domain.mapgakco.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MapgakcoRetrieveServiceTest {

  @InjectMocks private MapgakcoRetrieveService mapgakcoRetrieveService;

  @Mock private MapgakcoRepository mapgakcoRepository;

  @Mock private MapgakcoConverter mapgakcoConverter;
  
  @DisplayName("내가 모집한 맵각코를 확인할 수 있다")
  @Test 
  public void testGetAllMapgakcosHostedByMe() throws Exception {
    // given
    User user =
      User.builder()
        .name("함승훈")
        .course(new Course("FE"))
        .generation(new Generation(1))
        .email("email@gmail.com")
        .role(UserRole.STUDENT)
        .build();

    int size = 5;
    List<Mapgakco> mapgakcos = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      Mapgakco mapgakco = Mapgakco.builder()
        .applicantLimit(10)
        .user(user)
        .title("title")
        .content("content")
        .location("location")
        .northEastX(35.123)
        .northEastY(35.123)
        .southWestX(15.123)
        .southWestY(15.123)
        .meetingAt(LocalDateTime.MAX)
        .build();
      mapgakcos.add(mapgakco);
    }

    given(mapgakcoRepository.findMapgakcosHostedBy(user)).willReturn(mapgakcos);
    given(mapgakcoConverter.toMapgakcoInfo(any()))
        .willReturn(SimpleMapgakcoInfoDto.builder().build());
    // when
    List<SimpleMapgakcoInfoDto> results = mapgakcoRetrieveService.getAllMapgakcoInfoHostedBy(
      user);

    // then
    verify(mapgakcoRepository).findMapgakcosHostedBy(user);
    assertThat(results).hasSize(mapgakcos.size());
  }
  
  @DisplayName("내가 신청 맵각코를 확인할 수 있다")
  @Test 
  public void testGetAllMapgakcosApplieddByMe() throws Exception {
    // given
    User host =
      User.builder()
        .name("함승훈")
        .course(new Course("FE"))
        .generation(new Generation(1))
        .email("email@gmail.com")
        .role(UserRole.STUDENT)
        .build();
    User applicant =
      User.builder()
        .name("지원자")
        .course(new Course("FE"))
        .generation(new Generation(1))
        .email("email@gmail.com")
        .role(UserRole.STUDENT)
        .build();

    int size = 5;
    List<Mapgakco> mapgakcos = new ArrayList<>();
    List<MapgakcoApplicant> mapgakcoApplicants = new ArrayList<>();

    for (int i = 0; i < size; i++) {
      Mapgakco mapgakco = Mapgakco.builder()
        .applicantLimit(10)
        .user(host)
        .title("title")
        .content("content")
        .location("location")
        .northEastX(35.123)
        .northEastY(35.123)
        .southWestX(15.123)
        .southWestY(15.123)
        .meetingAt(LocalDateTime.MAX)
        .build();
      mapgakcos.add(mapgakco);
      mapgakcoApplicants.add(
          MapgakcoApplicant.builder().user(applicant).mapgakco(mapgakco).build());
    }

    given(mapgakcoRepository.findMapgakcosAppliedBy(applicant)).willReturn(mapgakcos);
    given(mapgakcoConverter.toMapgakcoInfo(any()))
        .willReturn(SimpleMapgakcoInfoDto.builder().build());
    // when
    List<SimpleMapgakcoInfoDto> results = mapgakcoRetrieveService.getAllMapgakcoInfoAppliedBy(
      applicant);

    // then
    verify(mapgakcoRepository).findMapgakcosAppliedBy(applicant);
    assertThat(results).hasSize(mapgakcos.size());
  }


}