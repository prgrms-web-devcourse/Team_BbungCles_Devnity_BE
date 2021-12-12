package com.devnity.devnity.domain.admin.service;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.admin.controller.dto.GenerationRequest;
import com.devnity.devnity.domain.admin.controller.dto.GenerationResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminGenerationService {

    private final GenerationRepository repository;

    private Generation findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException("기수 아이디로 코스를 찾을 수 없습니다. id: " + id, ErrorCode.ENTITY_NOT_FOUND));
    }

    public List<GenerationResponse> getAll() {
        return repository.findAll().stream().map(GenerationResponse::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = false)
    public boolean create(GenerationRequest req) {
        repository.save(req.to());
        return true;
    }

    @Transactional(readOnly = false)
    public boolean update(GenerationRequest req) {
        findById(req.getId()).updateSequence(req.getSequence());
        return true;
    }

    @Transactional(readOnly = false)
    public boolean delete(Long generationId) {
        repository.delete(findById(generationId));
        return true;
    }
}

