package com.devnity.devnity.domain.admin.dto.response;

import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class InvitationResponse {

  String course;
  Integer generation;
  UserRole role;

  public static InvitationResponse from(Invitation invitation) {
    return InvitationResponse.builder()
      .course(invitation.getCourse())
      .generation(invitation.getGeneration())
      .role(invitation.getRole())
      .build();
  }
}
