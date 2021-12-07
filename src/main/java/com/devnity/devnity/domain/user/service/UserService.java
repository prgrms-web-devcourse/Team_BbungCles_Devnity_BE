package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.response.UserInfoResponse;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserInfoResponse getUserInfoBy(Long userId) {
    UserDto userDto = retrieveUser(userId);
    return new UserInfoResponse(userDto);
  }

  private UserDto retrieveUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("There is no user for id = %d", userId)));

    return UserDto.builder()
        .userId(user.getId())
        .course(user.getCourse())
        .generation(user.getGeneration())
        .name(user.getName())
        .role(user.getRole().toString())
        .build();
  }
}
