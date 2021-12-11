package com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoApplicantConverter;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoFacadeService;
import com.devnity.devnity.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoApplicantService {

    private final MapgakcoApplicantConverter applicantConverter;
    private final MapgakcoApplicantRepository applicantRepository;
    private final MapgakcoFacadeService mapgakcoFacadeService;

    @Transactional
    public MapgakcoStatus applyForMapgakco(Long mapgakcoId, Long userId) {
        Mapgakco mapgakco = mapgakcoFacadeService.findMapgakcoById(mapgakcoId);
        if (!MapgakcoStatus.GATHERING.equals(mapgakco.getStatus())) {
            throw new BusinessException(String.format("The Mapgakco for %d is not GATHERING."
              , mapgakcoId), ErrorCode.MAPGAKCO_NOT_GATHERING);
        }

        User user = mapgakcoFacadeService.findUserById(userId);

        applicantRepository.save(applicantConverter.toApplicant(mapgakco, user));
        mapgakco.addApplicant();
        return mapgakco.getStatus();
    }

}
