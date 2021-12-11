package com.devnity.devnity.domain.admin.service;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.admin.controller.dto.CourseRequest;
import com.devnity.devnity.domain.admin.controller.dto.CourseResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCourseService {

    private final CourseRepository repository;

    private Course findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new BusinessException("코스 아이디로 코스를 찾을 수 없습니다. id: " + id, ErrorCode.ENTITY_NOT_FOUND));
    }

    public List<CourseResponse> getAll() {
        return repository.findAll().stream().map(CourseResponse::from).collect(Collectors.toList());
    }


    @Transactional(readOnly = false)
    public Boolean create(CourseRequest requestDto) {
        repository.save(requestDto.to());
        return true;
    }

    @Transactional(readOnly = false)
    public Boolean update(CourseRequest requestDto) {
        if (requestDto.getId() == null)
            throw new BusinessException("수정시 아이디는 필수입니다.", ErrorCode.INVALID_INPUT_VALUE);
        findById(requestDto.getId()).updateName(requestDto.getName());
        return true;
    }

    @Transactional(readOnly = false)
    public Boolean delete(Long id) {
        repository.delete(findById(id));
        return true;
    }
}
