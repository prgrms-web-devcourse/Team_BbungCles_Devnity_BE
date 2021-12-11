package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.admin.controller.dto.CourseRequest;
import com.devnity.devnity.domain.admin.controller.dto.CourseResponse;
import com.devnity.devnity.domain.admin.service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/courses")
public class AdminCourseController {

    private final AdminCourseService service;

    @GetMapping
    public ApiResponse<Map<String, List<CourseResponse>>> get() {
        return ApiResponse.ok(Collections.singletonMap("courses", service.getAll()));
    }

    @PostMapping
    public ApiResponse<Boolean> create(@RequestBody CourseRequest requestDto) {
        return ApiResponse.ok(service.create(requestDto));
    }

    @PutMapping("/{courseId}")
    public ApiResponse<Boolean> update(@PathVariable Long courseId, @RequestBody CourseRequest requestDto) {
        if (!courseId.equals(requestDto.getId()))
            throw new RuntimeException("Inconsistent course id");
        return ApiResponse.ok(service.update(requestDto));
    }

    @DeleteMapping("/{courseId}")
    public ApiResponse<Boolean> delete(@PathVariable Long courseId) {
        return ApiResponse.ok(service.delete(courseId));
    }

}
