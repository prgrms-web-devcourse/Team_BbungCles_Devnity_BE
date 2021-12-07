package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.domain.admin.controller.dto.CourseRequestDto;
import com.devnity.devnity.domain.admin.controller.dto.CourseResponseDto;
import com.devnity.devnity.domain.admin.service.AdminCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, List<CourseResponseDto>>> get() {
        return ResponseEntity.ok(Collections.singletonMap("courses", service.getAll()));
    }

    @PostMapping
    public ResponseEntity<Boolean> create(@RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.ok(service.create(requestDto));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody CourseRequestDto requestDto) {
        return ResponseEntity.ok(service.update(requestDto));
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long courseId) {
        return ResponseEntity.ok(service.delete(courseId));
    }

}
