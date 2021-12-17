package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.annotation.UserId;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoPageRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoPageResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoStatusResponse;
import com.devnity.devnity.domain.mapgakco.service.mapgakco.MapgakcoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoController {

    private final MapgakcoService mapgakcoService;

    @PostMapping("/mapgakcos")
    public ApiResponse<MapgakcoStatusResponse> createMapgakco(
      @UserId Long userId,
      @RequestBody MapgakcoCreateRequest request
    ) {
        return ApiResponse.ok(mapgakcoService.create(userId, request));
    }

    @GetMapping("/mapgakcos")
    public ApiResponse<List<MapgakcoPageResponse>> getMapgakco(
      @UserId Long userId,
      @RequestBody MapgakcoPageRequest request
    ) {
        return ApiResponse.ok(mapgakcoService.getMapgakcosByDist(request, userId));
    }

    @DeleteMapping("/mapgakcos/{mapgakcoId}")
    public ApiResponse<String> deleteMapgakco(@PathVariable Long mapgakcoId) {
        mapgakcoService.delete(mapgakcoId);
        return ApiResponse.ok();
    }

}

