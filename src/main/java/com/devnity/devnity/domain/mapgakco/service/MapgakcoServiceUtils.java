package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoServiceUtils {

  private final UserRepository userRepository;
  private final MapgakcoRepository mapgakcoRepository;

  public Mapgakco findMapgakcoById(Long mapgakcoId) {
    return mapgakcoRepository.findById(mapgakcoId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco for id = %d", mapgakcoId),
        ErrorCode.MAPGAKCO_NOT_FOUND));
  }

  public User findUserById(Long userId) {
    return UserServiceUtils.findUser(userRepository, userId);
  }

}
