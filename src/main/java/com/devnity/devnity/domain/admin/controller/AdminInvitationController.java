package com.devnity.devnity.domain.admin.controller;

import com.devnity.devnity.common.api.ApiResponse;
import com.devnity.devnity.domain.admin.controller.dto.InvitationRequest;
import com.devnity.devnity.domain.admin.controller.dto.InvitationResponse;
import com.devnity.devnity.domain.admin.service.AdminInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/link")
public class AdminInvitationController {

    private final AdminInvitationService service;

    @PostMapping
    public ApiResponse<Map<String, UUID>> create(@RequestBody InvitationRequest req) {
        var uuid = service.create(req);
        return ApiResponse.ok(Collections.singletonMap("uuid", uuid));
    }

    @GetMapping("/{uuid}")
    public ApiResponse<InvitationResponse> get(@PathVariable String uuid) {
        return ApiResponse.ok(service.get(UUID.fromString(uuid)));
    }

}
