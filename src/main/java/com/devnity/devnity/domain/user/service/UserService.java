package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.service.GatherRetrieveService;
import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.response.MyInfoResponse;
import com.devnity.devnity.domain.user.dto.response.UserGathersResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;

  private final UserRetrieveService userRetrieveService;

  private final GatherRetrieveService gatherRetrieveService;

  private final MapgakcoRetrieveService mapgakcoRetrieveService;

  private final CourseRepository courseRepository;

  private final GenerationRepository generationRepository;

  private final PasswordEncoder passwordEncoder;

  @Transactional
  public Long signUp(SignUpRequest request) {

    checkDuplicatedEmail(request.getEmail());

    Course course = UserServiceUtils.findCourse(courseRepository, request.getCourse());
    Generation generation = UserServiceUtils.findGeneration(generationRepository,
        request.getGeneration());
    User user = request.toEntity(passwordEncoder, course, generation);

    userRepository.save(user);

    return user.getId();
  }

  private void checkDuplicatedEmail(String email) {
    if (existsByEmail(email)) {
      throw new InvalidValueException(String.format("Email is duplicated. email=%s", email), ErrorCode.EMAIL_DUPLICATE);
    }
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public UserGathersResponse retrieveGathersHostedBy(Long userId) {
    User me = userRetrieveService.getUser(userId);

    List<SimpleGatherInfoDto> gathers = gatherRetrieveService.getGathersHostedBy(me);
    List<SimpleMapgakcoInfoDto> mapgakcos = mapgakcoRetrieveService.getAllMapgakcoInfoHostedBy(me);

    return UserGathersResponse.of(gathers, mapgakcos);
  }

  public UserGathersResponse retrieveGathersAppliedBy(Long userId) {
    User me = userRetrieveService.getUser(userId);

    List<SimpleGatherInfoDto> gathers = gatherRetrieveService.getGathersAppliedBy(me);
    List<SimpleMapgakcoInfoDto> mapgakcos = mapgakcoRetrieveService.getAllMapgakcoInfoAppliedBy(me);

    return UserGathersResponse.of(gathers, mapgakcos);
  }
}
