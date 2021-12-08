package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoService {

  private final MapgakcoRepository mapgakcoRepository;

}
