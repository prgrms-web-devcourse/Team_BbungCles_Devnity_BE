package com.devnity.devnity.web.mapgakco.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoDetailResponse;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoDomainController {

  private final MapgakcoDomainService domainService;

  @GetMapping("/mapgakcos/{mapgakcoId}")
  public ApiResponse<MapgakcoDetailResponse> getMapgakcoDetail(@PathVariable Long mapgakcoId) {
    return ApiResponse.ok(domainService.getDetail(mapgakcoId));
  }

}
