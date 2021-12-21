package com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoApplicantConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoStatusResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoApplicantService {

  private final MapgakcoApplicantConverter applicantConverter;
  private final MapgakcoApplicantRepository applicantRepository;
  private final MapgakcoRetrieveService mapgakcoRetrieveService;

  @Transactional
  public MapgakcoStatusResponse applyForMapgakco(Long mapgakcoId, Long userId) {
    Mapgakco mapgakco = mapgakcoRetrieveService.getMapgakcoById(mapgakcoId);
    if (!MapgakcoStatus.GATHERING.equals(mapgakco.getStatus())) {
      throw new BusinessException(
        String.format("The Mapgakco for %d is not GATHERING.", mapgakcoId),
        ErrorCode.MAPGAKCO_NOT_GATHERING);
    }
    User user = mapgakcoRetrieveService.getUserById(userId);
    applicantRepository.save(applicantConverter.toApplicant(mapgakco, user));
    mapgakco.addApplicant();
    return MapgakcoStatusResponse.of(mapgakco.getStatus());
  }

  public List<SimpleUserInfoDto> getAllApplicantByMapgakco(Mapgakco mapgakco) {
    return mapgakcoRetrieveService.getAllApplicantByMapgakcoWithUser(mapgakco).stream()
      .map(applicant -> SimpleUserInfoDto.of(applicant.getUser()))
      .collect(Collectors.toList());
  }

  @Transactional
  public void cancelForMapgakco(Long mapgakcoId, Long userId) {
    Mapgakco mapgakco = mapgakcoRetrieveService.getMapgakcoById(mapgakcoId);
    User user = mapgakcoRetrieveService.getUserById(userId);
    applicantRepository.delete(mapgakcoRetrieveService.getApplicantByMapgakcoAndUser(mapgakco, user));
    mapgakco.subApplicant();
  }

}
