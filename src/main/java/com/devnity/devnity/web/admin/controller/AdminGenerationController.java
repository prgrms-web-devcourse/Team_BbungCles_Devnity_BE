package com.devnity.devnity.web.admin.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.web.admin.dto.request.GenerationRequest;
import com.devnity.devnity.web.admin.dto.response.GenerationResponse;
import com.devnity.devnity.web.admin.service.AdminGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/generations")
public class AdminGenerationController {

  private final AdminGenerationService service;

  @PostMapping
  public ApiResponse<Boolean> create(@RequestBody GenerationRequest req) {
    return ApiResponse.ok(service.create(req));
  }

  @PutMapping("/{generationId}")
  public ApiResponse<Boolean> update(@PathVariable Long generationId, @RequestBody GenerationRequest req) {
    req.setId(generationId);
    return ApiResponse.ok(service.update(req));
  }

  @GetMapping
  public ApiResponse<Map<String, List<GenerationResponse>>> get() {
    return ApiResponse.ok(Collections.singletonMap("generations", service.getAll()));
  }

  @DeleteMapping("/{generationId}")
  public ApiResponse<Boolean> delete(@PathVariable Long generationId) {
    return ApiResponse.ok(service.delete(generationId));
  }
}
