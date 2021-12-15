package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.api.ApiResponse;
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
public class MapgakcoApplicantController {

    private final MapgakcoApplicantService mapgakcoApplicantService;

    @PostMapping("/mapgakcos/{mapgakcoId}/apply")
    public ApiResponse<MapgakcoStatus> applyMapgakco(
      @UserId Long userId,
      @PathVariable Long mapgakcoId
    ) {
        return ApiResponse.ok(
          mapgakcoApplicantService.applyForMapgakco(mapgakcoId, userId));
    }

    @DeleteMapping("/mapgakcos/{mapgakcoId}/apply")
    public ApiResponse<String> cancelMapgakco(
      @UserId Long userId,
      @PathVariable Long mapgakcoId) {
        mapgakcoApplicantService.cancelForMapgakco(mapgakcoId, userId);
        return ApiResponse.ok();
    }


}