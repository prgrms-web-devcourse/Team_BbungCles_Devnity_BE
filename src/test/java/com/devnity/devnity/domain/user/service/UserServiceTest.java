package com.devnity.devnity.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.service.GatherRetrieveService;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.response.UserGathersResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @InjectMocks private UserService userService;

  @Mock private UserRepository userRepository;

  @Mock private GenerationRepository generationRepository;

  @Mock private CourseRepository courseRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private UserRetrieveService userRetrieveService;

  @Mock
  private GatherRetrieveService gatherRetrieveService;

  @Mock
  private MapgakcoRetrieveService mapgakcoRetrieveService;


  @DisplayName("회원가입 할 수 있다")
  @Test 
  public void testSignUp() throws Exception {
    // given
    Generation generation = new Generation(1);
    Course course = new Course("FE");

    SignUpRequest request = SignUpRequest.builder()
        .course(course.getName())
        .email("email123@gmail.com")
        .name("seunghun")
        .password("password")
        .role(UserRole.STUDENT)
        .generation(generation.getSequence())
        .build();

    given(courseRepository.findByName(any())).willReturn(Optional.of(course));
    given(generationRepository.findBySequence(anyInt())).willReturn(Optional.of(generation));
    given(passwordEncoder.encode(any())).willReturn(request.getPassword());

    // when
    Long userId = userService.signUp(request);

    // then
    verify(userRepository).save(any());
  }

  @DisplayName("중복된 이메일은 회원가입할 수 없다")
  @Test
  public void testSignUpDuplicatedEmail() throws Exception {
    // given
    Generation generation = new Generation(1);
    Course course = new Course("FE");

    SignUpRequest request = SignUpRequest.builder()
        .course(course.getName())
        .email("user@gmail.com")
        .name("seunghun")
        .password("password")
        .role(UserRole.STUDENT)
        .generation(generation.getSequence())
        .build();

    given(userService.existsByEmail(any())).willReturn(true);

    // when // then
    assertThatThrownBy(() -> userService.signUp(request)).isInstanceOf(InvalidValueException.class);
  }
  
  @DisplayName("사용자가 모집한 모임을 확인할 수 있다")
  @Test 
  public void testFindGathersHostedMyMe() throws Exception {
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
    List<SimpleGatherInfoDto> gathers = new ArrayList<>();
    List<SimpleMapgakcoInfoDto> mapgakcos = new ArrayList<>();
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
      gathers.add(SimpleGatherInfoDto.of(gather));
    }

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
      SimpleMapgakcoInfoDto infoDto = SimpleMapgakcoInfoDto.builder()
        .mapgakcoId(mapgakco.getId())
        .status(mapgakco.getStatus())
        .title(mapgakco.getTitle())
        .location(mapgakco.getLocation())
        .meetingAt(mapgakco.getMeetingAt())
        .applicantLimit(mapgakco.getApplicantLimit())
        .applicantCount(mapgakco.getApplicantCount())
        .createdAt(mapgakco.getCreatedAt())
        .build();

      mapgakcos.add(infoDto);
    }

    given(userRetrieveService.getUser(anyLong())).willReturn(user);
    given(gatherRetrieveService.getGathersHostedBy(user)).willReturn(gathers);
    given(mapgakcoRetrieveService.getAllMapgakcoInfoHostedBy(user)).willReturn(mapgakcos);

    // when
    UserGathersResponse response = userService.getGathersHostedBy(1L);

    // then
    assertThat(response.getGathers()).hasSize(gathers.size());
    assertThat(response.getMapgakcos()).hasSize(mapgakcos.size());
  }
  @DisplayName("사용자가 모집한 모임을 확인할 수 있다")
  @Test
  public void testFindGathersAppliedMyMe() throws Exception {
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
        .name("함승훈")
        .course(new Course("FE"))
        .generation(new Generation(1))
        .email("email@gmail.com")
        .role(UserRole.STUDENT)
        .build();

    int size = 5;
    List<SimpleGatherInfoDto> gathers = new ArrayList<>();
    List<SimpleMapgakcoInfoDto> mapgakcos = new ArrayList<>();
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

      GatherApplicant ga = new GatherApplicant(applicant, gather);
      gather.addApplicant(ga);
      gathers.add(SimpleGatherInfoDto.of(gather));
    }

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

      SimpleMapgakcoInfoDto infoDto = SimpleMapgakcoInfoDto.builder()
        .mapgakcoId(mapgakco.getId())
        .status(mapgakco.getStatus())
        .title(mapgakco.getTitle())
        .location(mapgakco.getLocation())
        .meetingAt(mapgakco.getMeetingAt())
        .applicantLimit(mapgakco.getApplicantLimit())
        .applicantCount(mapgakco.getApplicantCount())
        .createdAt(mapgakco.getCreatedAt())
        .build();

      mapgakcos.add(infoDto);
    }

    given(userRetrieveService.getUser(anyLong())).willReturn(applicant);
    given(gatherRetrieveService.getGathersAppliedBy(applicant)).willReturn(gathers);
    given(mapgakcoRetrieveService.getAllMapgakcoInfoAppliedBy(applicant)).willReturn(mapgakcos);

    // when
    UserGathersResponse response = userService.getGathersAppliedBy(1L);

    // then
    assertThat(response.getGathers()).hasSize(gathers.size());
    assertThat(response.getMapgakcos()).hasSize(mapgakcos.size());
  }
}