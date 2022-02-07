package com.devnity.devnity.domain.gather.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class GatherRetrieveServiceTest {

  @InjectMocks
  private GatherRetrieveService gatherRetrieveService;

  @Mock
  private GatherRepository gatherRepository;

  @DisplayName("내가 모집한 모임을 확인할 수 있다")
  @Test
  public void testGathersHostedByMe() throws Exception {
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
    List<Gather> gathers = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      CreateGatherRequest request =
        CreateGatherRequest.builder()
          .applicantLimit(10)
          .category(GatherCategory.STUDY)
          .content("content")
          .title("title")
          .deadline(LocalDate.now().plusDays(10))
          .build();
      Gather gather = Gather.of(user, request);
      gathers.add(gather);
    }

    given(gatherRepository.findGathersHostedBy(user)).willReturn(gathers);
    // when
    List<SimpleGatherInfoDto> results = gatherRetrieveService.getGathersHostedBy(user);

    // then
    verify(gatherRepository).findGathersHostedBy(user);
    assertThat(results).hasSize(gathers.size());
  }

  @DisplayName("내가 지원한 모임을 확인할 수 있다")
  @Test
  public void testGathersAppliedByMe() throws Exception {
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
        .email("applicant@gmail.com")
        .role(UserRole.STUDENT)
        .build();

    int size = 5;
    List<Gather> gathers = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      CreateGatherRequest request =
        CreateGatherRequest.builder()
          .applicantLimit(10)
          .category(GatherCategory.STUDY)
          .content("content")
          .title("title")
          .deadline(LocalDate.now().plusDays(10))
          .build();
      Gather gather = Gather.of(host, request);

      GatherApplicant ga = GatherApplicant.of(host, gather);

      gathers.add(gather);
    }

    given(gatherRepository.findGathersAppliedBy(applicant)).willReturn(gathers);
    // when
    List<SimpleGatherInfoDto> results = gatherRetrieveService.getGathersAppliedBy(applicant);

    // then
    verify(gatherRepository).findGathersAppliedBy(applicant);
    assertThat(results).hasSize(gathers.size());
  }


}