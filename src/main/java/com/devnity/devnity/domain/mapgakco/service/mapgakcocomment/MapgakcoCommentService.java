package com.devnity.devnity.domain.mapgakco.service.mapgakcocomment;

import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoCommentService {

    private final MapgakcoCommentRepository mapgakcoCommentRepository;

}
