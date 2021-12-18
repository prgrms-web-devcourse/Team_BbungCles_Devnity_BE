package com.devnity.devnity.domain.mapgakco.service.mapgakco;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoConverter;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoPageRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoUpdateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoPageResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoStatusResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.service.MapService;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.entity.User;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoService {

  private final MapgakcoConverter mapgakcoConverter;
  private final MapgakcoRepository mapgakcoRepository;
  private final MapgakcoRetrieveService mapgakcoRetrieveService;
  private final MapService mapService;

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

  /**
   * 초기요청 또는 중심점 변경시 lastDistance는 0.0으로 요청해야 함 이전에 조회된 내용이면 lastDistance만 그대로, 나머지는 null반환
   */
  public MapgakcoPageResponse getMapgakcosByDist(MapgakcoPageRequest request) {
    Double centerX = request.getCenterX();
    Double centerY = request.getCenterY();
    Double currentDistance = mapService.maxDistanceByTwoPoint(centerX, centerY,
      request.getCurrentNEX(), request.getCurrentNEY(), request.getCurrentSWX(), request.getCurrentSWY(), "meter");
    Double lastDistance = request.getLastDistance();

    if (currentDistance <= lastDistance) {
      return MapgakcoPageResponse.builder().mapgakcos(null).lastDistance(lastDistance).hasNext(null).build();
    }

    List<Pair<Double, Mapgakco>> mapgakcoArr =
      mapgakcoRetrieveService.getAllMapgakco().stream()
        .map(mapgakco -> Pair.of(mapService.distance(centerX, centerY, mapgakco.getLatitude(), mapgakco.getLongitude(), "meter"), mapgakco))
        .sorted(Comparator.comparing(Pair::getFirst))
        .collect(Collectors.toList());

    Boolean hasNext = mapgakcoArr.stream().anyMatch(pr -> pr.getFirst() > currentDistance);

    List<SimpleMapgakcoInfoDto> mapgakcos = mapgakcoArr.stream()
      .filter(pr -> pr.getFirst() >= lastDistance && pr.getFirst() < currentDistance)
      .map(pr -> mapgakcoConverter.toMapgakcoInfo(pr.getSecond()))
      .collect(Collectors.toList());

    return MapgakcoPageResponse.of(mapgakcos, currentDistance, hasNext);
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
