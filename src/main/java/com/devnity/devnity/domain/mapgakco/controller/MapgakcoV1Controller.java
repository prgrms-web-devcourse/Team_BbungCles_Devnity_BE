package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

}

