package com.devnity.devnity.domain.user.service;

import static com.devnity.devnity.common.error.exception.ErrorCode.ENTITY_NOT_FOUND;
import static com.devnity.devnity.common.error.exception.ErrorCode.USER_NOT_FOUND;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.web.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.web.mapgakco.service.MapService;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.web.user.dto.SimpleUserMapInfoDto;
import com.devnity.devnity.web.user.dto.UserDto;
import com.devnity.devnity.web.user.dto.response.MyInfoResponse;
import com.devnity.devnity.web.user.dto.response.UserMapPageResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.devnity.devnity.web.user.service.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserRetrieveService {

  private final UserRepository userRepository;

  private final CourseRepository courseRepository;

  private final GenerationRepository generationRepository;

  private final MapService mapService;

  //== Entity 반환 메서드 ==//
  public User getUser(Long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no user for id = %d", userId), USER_NOT_FOUND));
  }

  public List<User> getAllByCourseAndGenerationLimit(User user, int limit) {
    return userRepository
      .findAllByCourseAndGenerationLimit(user, limit);
  }

  public Generation findGeneration(Integer sequence) {
    return generationRepository.findBySequence(sequence)
      .orElseThrow(() -> new EntityNotFoundException(String.format("There is no generation for sequence = %d", sequence), ENTITY_NOT_FOUND));
  }

  public Course findCourse(String name) {
    return courseRepository.findByName(name)
      .orElseThrow(() -> new EntityNotFoundException(String.format("There is no course for name = %s", name), ENTITY_NOT_FOUND));
  }

  //== DTO 반환 메서드 ==//
  public MyInfoResponse getMyInfo(Long userId) {

    UserServiceUtils.notNull(userId, "userId must be provided");

    User user = getUser(userId);
    Introduction introduction = user.getIntroduction();
    return new MyInfoResponse(UserDto.of(user), IntroductionDto.of(introduction, introduction.getDescription()));
  }

  public SimpleUserInfoDto getSimpleUserInfo(Long userId) {

    UserServiceUtils.notNull(userId, "userId must be provided");

    User user = getUser(userId);
    return SimpleUserInfoDto.of(user, user.getIntroduction().getProfileImgUrl());
  }

  public UserMapPageResponse getAllUserByDist(
    String course, Integer generation, UserRole role, String name,
    Double lastDistance, Double centerY, Double centerX,
    Double ney, Double nex, Double swy, Double swx, String unit
  ) {
    Course crs = course != null ? findCourse(course) : null;
    Generation gen = generation != null ? findGeneration(generation) : null;

    Double currentDistance = mapService.maxDistanceByTwoPoint(centerY, centerX, ney, nex, swy, swx, unit);

    if (currentDistance <= lastDistance) {
      return UserMapPageResponse.builder().lastDistance(lastDistance).hasNext(null).users(null).build();
    }

    List<User> foundUsers = userRepository.getAllByCourseByFilter(crs, gen, role, name);
    List<Pair<Double, User>> valueArr = new ArrayList<>();
    for (User user : foundUsers) {
      Double latitude = user.getIntroduction().getLatitude();
      Double longitude = user.getIntroduction().getLongitude();
      if (latitude == null || longitude == null) {
        continue;
      }
      Double meter = mapService.distance(centerY, centerX, latitude, longitude, unit);
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

    return UserMapPageResponse.builder()
      .lastDistance(currentDistance)
      .hasNext(hasNext)
      .users(users)
      .build();
  }
}
