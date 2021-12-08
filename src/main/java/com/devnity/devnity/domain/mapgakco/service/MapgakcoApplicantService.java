package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoApplicantService {

    private final MapgakcoApplicantRepository mapgakcoApplicantRepository;

}
