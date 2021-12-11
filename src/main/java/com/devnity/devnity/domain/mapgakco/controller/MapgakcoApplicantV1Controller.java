package com.devnity.devnity.domain.mapgakco.controller;

import com.devnity.devnity.common.config.security.jwt.JwtAuthentication;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant.MapgakcoApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<MapgakcoStatus> applyMapgakco(
      @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
      @PathVariable Long mapgakcoId
    ) {
        return ResponseEntity.ok(
          mapgakcoApplicantService.applyForMapgakco(mapgakcoId, jwtAuthentication.getUserId()));
    }

}
