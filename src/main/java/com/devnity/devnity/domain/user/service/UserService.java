package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.service.GatherRetrieveService;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.service.MapService;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.dto.SimpleUserMapInfoDto;
import com.devnity.devnity.domain.user.dto.request.SignUpRequest;
import com.devnity.devnity.domain.user.dto.request.UserMapPageRequest;
import com.devnity.devnity.domain.user.dto.response.UserGathersResponse;
import com.devnity.devnity.domain.user.dto.response.UserMapPageResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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

  private final MapService mapService;

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

  public UserGathersResponse getGathersHostedBy(Long userId) {
    User me = userRetrieveService.getUser(userId);

    List<SimpleGatherInfoDto> gathers = gatherRetrieveService.getGathersHostedBy(me);
    List<SimpleMapgakcoInfoDto> mapgakcos = mapgakcoRetrieveService.getAllMapgakcoInfoHostedBy(me);

    return UserGathersResponse.of(gathers, mapgakcos);
  }

  public UserGathersResponse getGathersAppliedBy(Long userId) {
    User me = userRetrieveService.getUser(userId);

    List<SimpleGatherInfoDto> gathers = gatherRetrieveService.getGathersAppliedBy(me);
    List<SimpleMapgakcoInfoDto> mapgakcos = mapgakcoRetrieveService.getAllMapgakcoInfoAppliedBy(me);

    return UserGathersResponse.of(gathers, mapgakcos);
  }

  public UserMapPageResponse<SimpleUserMapInfoDto> getUsersByDist(String course, Integer generation, UserMapPageRequest request) {
    Course crs = course != null ? userRetrieveService.findCourse(course) : null;
    Generation gen = generation != null ? userRetrieveService.findGeneration(generation) : null;

    Double centerX = request.getCenterX();
    Double centerY = request.getCenterY();
    Double currentDistance = mapService.maxDistanceByTwoPoint(centerX, centerY,
      request.getCurrentNEX(), request.getCurrentNEY(), request.getCurrentSWX(), request.getCurrentSWY(), "meter");
    Double lastDistance = request.getLastDistance();

    if (currentDistance <= lastDistance) {
      return UserMapPageResponse.<SimpleUserMapInfoDto>builder().lastDistance(lastDistance).hasNext(null).values(null).build();
    }

    List<User> foundUsers = userRepository.getAllByCourseAndGeneration(crs, gen);
    List<Pair<Double, User>> valueArr = new ArrayList<>();
    for (User user : foundUsers) {
      Double latitude = user.getIntroduction().getLatitude();
      Double longitude = user.getIntroduction().getLongitude();
      if (latitude == null || longitude == null) {
        continue;
      }
      Double meter = mapService.distance(centerX, centerY, latitude, longitude, "meter");
      valueArr.add(Pair.of(meter, user));
    }

    Boolean hasNext = valueArr.stream().anyMatch(pr -> pr.getFirst() > currentDistance);

    List<SimpleUserMapInfoDto> users = valueArr.stream()
      .filter(pr -> pr.getFirst() >= lastDistance && pr.getFirst() < currentDistance)
      .map(pr -> SimpleUserMapInfoDto.of(
        pr.getSecond(),
        pr.getSecond().getIntroduction().getProfileImgUrl(),
        pr.getSecond().getIntroduction().getLatitude(),
        pr.getSecond().getIntroduction().getLongitude()))
      .collect(Collectors.toList());

    return UserMapPageResponse.<SimpleUserMapInfoDto>builder()
      .lastDistance(currentDistance)
      .hasNext(hasNext)
      .values(users)
      .build();
  }
}
