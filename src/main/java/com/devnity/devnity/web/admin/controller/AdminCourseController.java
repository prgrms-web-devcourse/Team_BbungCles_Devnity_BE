package com.devnity.devnity.web.admin.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.admin.dto.request.CourseRequest;
import com.devnity.devnity.domain.admin.dto.response.CourseResponse;
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
  public ApiResponse<Boolean> create(@RequestBody CourseRequest req) {
    return ApiResponse.ok(service.create(req));
  }

  @PutMapping("/{courseId}")
  public ApiResponse<Boolean> update(@PathVariable Long courseId, @RequestBody CourseRequest req) {
    req.setId(courseId);
    return ApiResponse.ok(service.update(req));
  }

  @DeleteMapping("/{courseId}")
  public ApiResponse<Boolean> delete(@PathVariable Long courseId) {
    return ApiResponse.ok(service.delete(courseId));
  }

}
