package com.devnity.devnity.domain.admin.service;

import com.devnity.devnity.domain.admin.controller.dto.CourseRequest;
import com.devnity.devnity.domain.admin.controller.dto.CourseResponse;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCourseService {

    private final CourseRepository repository;

    private Course findById(Long id) {
        // TODO("Change to Business Exception")
        return repository.findById(id).orElseThrow(() -> new RuntimeException("No course found with id: " + id));
    }

    public List<CourseResponse> getAll() {
        return repository.findAll().stream().map(CourseResponse::from).toList();
    }


    @Transactional(readOnly = false)
    public Boolean create(CourseRequest requestDto) {
        repository.save(requestDto.to());
        return true;
    }

    @Transactional(readOnly = false)
    public Boolean update(CourseRequest requestDto) {
        Assert.notNull(requestDto.getId(), "Id Cannot be null on update request.");
        findById(requestDto.getId()).updateName(requestDto.getName());
        return true;
    }

    @Transactional(readOnly = false)
    public Boolean delete(Long id) {
        repository.delete(findById(id));
        return true;
    }
}
