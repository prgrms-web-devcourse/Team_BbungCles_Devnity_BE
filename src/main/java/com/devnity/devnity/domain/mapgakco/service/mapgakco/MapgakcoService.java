package com.devnity.devnity.domain.mapgakco.service.mapgakco;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoPageRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoUpdateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoPageResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoStatusResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;
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

  public MapgakcoResponse getMapgakco(Long mapgakcoId) {
    return mapgakcoConverter.toMapgakcoResponse(mapgakcoRetrieveService.getMapgakcoById(mapgakcoId));
  }

  public MapgakcoResponse getMapgakco(Mapgakco mapgakco) {
    return mapgakcoConverter.toMapgakcoResponse(mapgakco);
  }

  public MapgakcoPageResponse getMapgakcosByDist(MapgakcoPageRequest request) {
    return mapgakcoRetrieveService.getMapgakcosByDist(
      request.getLastDistance(), request.getCenterX(), request.getCenterY(),
      request.getCurrentNEX(), request.getCurrentNEY(), request.getCurrentSWX(), request.getCurrentSWY());
  }

  public List<SimpleMapgakcoInfoDto> getMapgakcosWithinRange(MapgakcoRequest request) {
    Double centerX = (request.getCurrentNEX() + request.getCurrentSWX()) / 2;
    Double centerY = (request.getCurrentNEY() + request.getCurrentSWY()) / 2;

    MapgakcoPageResponse mapgakcosByDist = mapgakcoRetrieveService.getMapgakcosByDist(
      0.0, centerX, centerY,
      request.getCurrentNEX(), request.getCurrentNEY(), request.getCurrentSWX(), request.getCurrentSWY());

    return mapgakcosByDist.getMapgakcos();
  }

  @Transactional
  public MapgakcoStatusResponse updateMapgakco(Long mapgakcoId, MapgakcoUpdateRequest request) {
    Mapgakco mapgakco = mapgakcoRetrieveService.getMapgakcoById(mapgakcoId);
    if (mapgakco.getMeetingAt().isAfter(request.getMeetingAt())) {
      throw new InvalidValueException(ErrorCode.INVALID_MEETINGAT);
    }

    mapgakco = mapgakco.update(request.getTitle(), request.getContent(), request.getLocation(),
      request.getLatitude(), request.getLongitude(), request.getMeetingAt());
    return MapgakcoStatusResponse.of(mapgakco.getStatus());
  }

  @Transactional
  public void deleteMapgakco(Long mapgakcoId) {
    mapgakcoRetrieveService.getMapgakcoById(mapgakcoId).delete();
  }

}
