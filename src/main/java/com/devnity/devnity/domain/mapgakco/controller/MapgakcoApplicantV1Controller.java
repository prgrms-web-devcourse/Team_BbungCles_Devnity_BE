package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.common.config.security.jwt.JwtAuthentication;
import com.devnity.devnity.common.config.security.resolver.UserId;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant.MapgakcoApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class MapgakcoApplicantV1Controller {

    private final MapgakcoApplicantService mapgakcoApplicantService;

    @PostMapping("/mapgakcos/{id}/apply")
    public ApiResponse<MapgakcoStatus> applyMapgakco(
      @UserId JwtAuthentication jwtAuthentication,
      @PathVariable Long mapgakcoId
    ) {
        return ApiResponse.ok(
          mapgakcoApplicantService.applyForMapgakco(mapgakcoId, jwtAuthentication.getUserId()));
    }

    @DeleteMapping("/mapgakcos/{id}/apply")
    public ApiResponse<String> cancelMapgakco(
      @UserId JwtAuthentication jwtAuthentication,
      @PathVariable Long mapgakcoId) {
        mapgakcoApplicantService.cancelForMapgakco(mapgakcoId, jwtAuthentication.getUserId());
        return ApiResponse.ok();
    }

}
