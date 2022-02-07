package com.devnity.devnity.web.mapgakco.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.web.common.config.security.cors.annotation.UserId;
import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoPageRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoUpdateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoCreateResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoPageResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoStatusResponse;
import com.devnity.devnity.domain.mapgakco.service.mapgakco.MapgakcoService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ApiResponse<MapgakcoCreateResponse> createMapgakco(
      @UserId Long userId,
      @RequestBody @Validated MapgakcoCreateRequest request
    ) {
        return ApiResponse.ok(mapgakcoService.create(userId, request));
    }

    @GetMapping("/mapgakcos")
    public ApiResponse<MapgakcoPageResponse> getMapgakcos(@ModelAttribute @Valid MapgakcoPageRequest request) {
        return ApiResponse.ok(mapgakcoService.getMapgakcosByDist(request));
    }

    @GetMapping("/mapgakcos/range")
    public ApiResponse<List<SimpleMapgakcoInfoDto>> getMapgakcosWithinRange(@ModelAttribute @Valid MapgakcoRequest request) {
        return ApiResponse.ok(mapgakcoService.getMapgakcosWithinRange(request));
    }

    @PatchMapping("/mapgakcos/{mapgakcoId}")
    public ApiResponse<MapgakcoStatusResponse> updateMapgakco(
      @UserId Long userId,
      @PathVariable Long mapgakcoId,
      @RequestBody @Valid MapgakcoUpdateRequest request
    ) {
        return ApiResponse.ok(mapgakcoService.updateMapgakco(userId, mapgakcoId, request));
    }

    @DeleteMapping("/mapgakcos/{mapgakcoId}")
    public ApiResponse<String> deleteMapgakco(
      @UserId Long userId,
      @PathVariable Long mapgakcoId) {
        mapgakcoService.deleteMapgakco(userId, mapgakcoId);
        return ApiResponse.ok();
    }

}

