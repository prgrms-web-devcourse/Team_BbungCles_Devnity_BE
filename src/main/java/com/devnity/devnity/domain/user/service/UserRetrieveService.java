package com.devnity.devnity.domain.user.service;

import static com.devnity.devnity.common.error.exception.ErrorCode.USER_NOT_FOUND;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.response.MyInfoResponse;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserRetrieveService {

  private final UserRepository userRepository;

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
  
  //== DTO 반환 메서드 ==//
  public MyInfoResponse fetchUserInfo(Long userId) {

    UserServiceUtils.notNull(userId, "userId must be provided");

    User user = getUser(userId);
    Introduction introduction = user.getIntroduction();
    return new MyInfoResponse(UserDto.of(user), IntroductionDto.of(introduction, introduction.getContent()));
  }

  public SimpleUserInfoDto getSimpleUserInfo(Long userId) {

    UserServiceUtils.notNull(userId, "userId must be provided");

    User user = getUser(userId);
    return SimpleUserInfoDto.of(user, user.getIntroduction().getProfileImgUrl());
  }
}
