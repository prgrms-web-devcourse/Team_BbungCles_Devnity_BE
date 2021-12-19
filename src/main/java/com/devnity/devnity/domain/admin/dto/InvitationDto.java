package com.devnity.devnity.domain.admin.dto;

import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InvitationDto {

  String uuid;
  String course;
  Integer generation;
  UserRole role;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  LocalDateTime deadline;

  public static InvitationDto of(Invitation invitation){
    return InvitationDto.builder()
      .uuid(invitation.getUuid())
      .course(invitation.getCourse())
      .generation(invitation.getGeneration())
      .role(invitation.getRole())
      .deadline(invitation.getDeadline().getDeadline())
      .build();
  }

}
