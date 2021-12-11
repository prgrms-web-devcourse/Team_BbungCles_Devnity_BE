//package com.devnity.devnity.domain.mapgakco.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.then;
//
//import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
//import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
//import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
//import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
//import com.devnity.devnity.domain.user.entity.Authority;
//import com.devnity.devnity.domain.user.entity.Course;
//import com.devnity.devnity.domain.user.entity.Generation;
//import com.devnity.devnity.domain.user.entity.User;
//import com.devnity.devnity.domain.user.entity.UserRole;
//import com.devnity.devnity.domain.user.repository.UserRepository;
//import java.time.LocalDateTime;
//import java.util.Optional;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//@ExtendWith(MockitoExtension.class)
//class MapgakcoServiceTest {
//
//  @InjectMocks
//  private MapgakcoService mapgakcoService;
//  @Mock
//  private MapgakcoConverter mapgakcoConverter;
//  @Mock
//  private MapgakcoRepository mapgakcoRepository;
//  @Mock
//  private UserRepository userRepository;
//
//  @Test
//  @DisplayName("맵각코를 등록할 수 있다.")
//  public void shouldHaveCreateMapgakco() {
//    // given
//    Generation generation = new Generation(1);
//    Course course = new Course("FE");
//    User user = User.builder()
//      .email("email@gmail.com")
//      .course(course)
//      .generation(generation)
//      .password("password")
//      .name("seunghun")
//      .role(UserRole.STUDENT)
//      .authority(Authority.USER)
//      .build();
//
//    MapgakcoCreateRequest request = MapgakcoCreateRequest.builder()
//      .title("맵각코")
//      .applicantLimit(5)
//      .deadline(LocalDateTime.now())
//      .content("모각코 모집중")
//      .location("어대역 5번출구")
//      .latitude(12.5)
//      .longitude(12.5)
//      .meetingAt(LocalDateTime.now())
//      .build();
//
//    Mapgakco mapgakco = Mapgakco.builder()
//      .title("맵각코")
//      .applicantLimit(5)
//      .deadline(LocalDateTime.now())
//      .content("모각코 모집중")
//      .location("어대역 5번출구")
//      .latitude(12.5)
//      .longitude(12.5)
//      .meetingAt(LocalDateTime.now())
//      .user(user)
//      .build();
//
//    assertEquals(0, mapgakco.getView());
//    given(userRepository.findById(any())).willReturn(Optional.of(user));
//    given(mapgakcoConverter.toMapgakco(user, request)).willReturn(mapgakco);
//    given(mapgakcoRepository.save(mapgakco)).willReturn(mapgakco);
//
//    // when
//    mapgakcoService.create(1L, request);
//
//    // then
//    then(mapgakcoConverter).should().toMapgakco(user, request);
//    then(mapgakcoRepository).should().save(mapgakco);
//
//  }
//
//}