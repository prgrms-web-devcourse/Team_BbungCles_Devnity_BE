package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.service.mapgakco.MapgakcoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoV1Controller {

    private final MapgakcoService mapgakcoService;

    @PostMapping("/mapgakcos")
    public ApiResponse<MapgakcoStatus> createMapgakco(
      @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
      @RequestBody MapgakcoCreateRequest request
    ) {
        return ApiResponse.ok(mapgakcoService.create(jwtAuthentication.getUserId(), request));
    }

    @DeleteMapping("/mapgakcos/{id}")
    public ApiResponse<String> deleteMapgakco(@PathVariable Long mapgakcoId) {
        mapgakcoService.delete(mapgakcoId);
        return ApiResponse.ok();
    }

}

