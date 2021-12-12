package com.devnity.devnity.domain.admin.controller.dto;

import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvitationResponse {
    String course;
    Integer generation;
    UserRole role;

    public static InvitationResponse from(Invitation invitation) {
        return new InvitationResponse(invitation.getCourse(), invitation.getGeneration(), invitation.getRole());
    }
}
