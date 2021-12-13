package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.exception.UserNotFoundException;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserRetrieveService {

  private final UserRepository userRepository;

  public User getUser(Long userId) {
    return userRepository.findById(userId)
      .orElseThrow(() -> new UserNotFoundException(
        String.format("There is no user for id = %d", userId)));
  }

  public UserInfoResponse fetchUserInfo(Long userId) {

    UserServiceUtils.notNull(userId, "userId must be provided");

    User user = getUser(userId);
    Introduction introduction = user.getIntroduction();
    return new UserInfoResponse(UserDto.of(user), IntroductionDto.of(introduction, introduction.getContent()));
  }

  public SimpleUserInfoDto getSimpleUserInfo(Long userId) {

    UserServiceUtils.notNull(userId, "userId must be provided");

    User user = getUser(userId);
    return SimpleUserInfoDto.of(user, user.getIntroduction().getProfileImgUrl());
  }
}
