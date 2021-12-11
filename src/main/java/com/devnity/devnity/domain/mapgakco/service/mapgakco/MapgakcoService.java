package com.devnity.devnity.domain.mapgakco.service.mapgakco;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoServiceUtils;
import com.devnity.devnity.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoService {

  private final MapgakcoConverter mapgakcoConverter;
  private final MapgakcoRepository mapgakcoRepository;
  private final MapgakcoServiceUtils mapgakcoServiceUtils;

  @Transactional
  public MapgakcoStatus create(Long userId, MapgakcoCreateRequest request) {
    User user = mapgakcoServiceUtils.findUserById(userId);
    return mapgakcoRepository.save(mapgakcoConverter.toMapgakco(user, request)).getStatus();
  }

  @Transactional
  public void delete(Long mapgakcoId) {
    mapgakcoServiceUtils.findMapgakcoById(mapgakcoId).delete();
  }

}
