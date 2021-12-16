package com.devnity.devnity.domain.mapgakco.service.mapgakco;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoStatusResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
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
  private final MapgakcoRetrieveService mapgakcoRetrieveService;

  @Transactional
  public MapgakcoStatusResponse create(Long userId, MapgakcoCreateRequest request) {
    User user = mapgakcoRetrieveService.getUserById(userId);
    return MapgakcoStatusResponse.of(
      mapgakcoRepository.save(mapgakcoConverter.toMapgakco(user, request)).getStatus());
  }

  @Transactional
  public void delete(Long mapgakcoId) {
    mapgakcoRetrieveService.getMapgakcoById(mapgakcoId).delete();
  }

  public MapgakcoResponse getMapgakco(Long mapgakcoId) {
    return mapgakcoConverter.toMapgakcoResponse(
      mapgakcoRetrieveService.getMapgakcoById(mapgakcoId));
  }

  public MapgakcoResponse getMapgakco(Mapgakco mapgakco) {
    return mapgakcoConverter.toMapgakcoResponse(mapgakco);
  }
}
