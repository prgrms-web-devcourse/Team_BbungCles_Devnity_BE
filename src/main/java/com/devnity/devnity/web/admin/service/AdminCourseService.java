package com.devnity.devnity.web.admin.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.admin.dto.request.CourseRequest;
import com.devnity.devnity.domain.admin.dto.response.CourseResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCourseService {

  private final CourseRepository repository;

  private Course findById(Long id) {
    return repository.findById(id).orElseThrow(() ->
      new EntityNotFoundException("코스 아이디로 코스를 찾을 수 없습니다. id: " + id, ErrorCode.ENTITY_NOT_FOUND));
  }

  public List<CourseResponse> getAll() {
    return repository.findAll().stream()
      .map(CourseResponse::from).collect(Collectors.toList());
  }

  @Transactional(readOnly = false)
  public Boolean create(CourseRequest requestDto) {
    repository.save(requestDto.to());
    return true;
  }

  @Transactional(readOnly = false)
  public Boolean update(CourseRequest requestDto) {
    findById(requestDto.getId()).updateName(requestDto.getName());
    return true;
  }

  @Transactional(readOnly = false)
  public Boolean delete(Long id) {
    repository.delete(findById(id));
    return true;
  }
}
