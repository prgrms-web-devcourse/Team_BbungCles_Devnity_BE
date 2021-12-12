package com.devnity.devnity.domain.admin.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class InvitationRequest {
    @NotBlank
    String course;

    @NotNull
    Integer generation;

    @NotBlank
    String role;
}
