package com.devnity.devnity.web.auth.dto.response;

import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpInfoResponse {

  String course;
  Integer generation;
  UserRole role;

  public static SignUpInfoResponse of(Invitation invitation) {
    return SignUpInfoResponse.builder()
      .course(invitation.getCourse())
      .generation(invitation.getGeneration())
      .role(invitation.getRole())
      .build();
  }
}
