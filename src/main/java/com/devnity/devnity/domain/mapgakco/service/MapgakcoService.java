package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoService {

  private final MapgakcoConverter mapgakcoConverter;
  private final MapgakcoRepository mapgakcoRepository;
  private final UserRepository userRepository;

  @Transactional
  public MapgakcoStatus create(Long userId, MapgakcoCreateRequest request) {
    // Todo : 중복코드 리펙토링 -> facade 클래스
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new EntityNotFoundException("User is not found."));
    return mapgakcoRepository.save(mapgakcoConverter.toMapgakco(user, request)).getStatus();
  }

}
