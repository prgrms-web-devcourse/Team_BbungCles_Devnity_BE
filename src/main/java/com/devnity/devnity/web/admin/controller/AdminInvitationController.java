package com.devnity.devnity.web.admin.controller;

import com.devnity.devnity.web.common.dto.ApiResponse;
import com.devnity.devnity.domain.admin.dto.InvitationDto;
import com.devnity.devnity.domain.admin.dto.request.InvitationRequest;
import com.devnity.devnity.domain.admin.service.AdminInvitationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/links")
public class AdminInvitationController {

  private final AdminInvitationService service;

  @PostMapping
  public ApiResponse<Map<String, String>> create(@RequestBody InvitationRequest req) {
    var uuid = service.create(req);
    return ApiResponse.ok(Collections.singletonMap("uuid", uuid));
  }

  @DeleteMapping("/{uuid}")
  public ApiResponse<String> delete(@PathVariable("uuid") String uuid){
    String response = service.delete(uuid);
    return ApiResponse.ok(response);
  }

  @GetMapping
  public ApiResponse<List<InvitationDto>> getAll(){
    List<InvitationDto> response = service.getAll();
    return ApiResponse.ok(response);
  }

}
