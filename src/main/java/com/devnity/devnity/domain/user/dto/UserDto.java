package com.devnity.devnity.domain.user.dto;

import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

  private Long userId;
  private String email;
  private String name;
  private String course;
  private Integer generation;
  private UserRole role;
  private LocalDateTime createdAt;

  @Builder
  public UserDto(Long userId, String email, String name, String course, Integer generation,
      UserRole role, LocalDateTime createdAt) {
    this.userId = userId;
    this.email = email;
    this.name = name;
    this.course = course;
    this.generation = generation;
    this.role = role;
    this.createdAt = createdAt;
  }

  public static UserDto of(User user) {
    return UserDto.builder()
        .userId(user.getId())
        .name(user.getName())
        .course(user.getCourseName())
        .generation(user.getGenerationSequence())
        .role(user.getRole())
        .createdAt(user.getCreatedAt())
        .build();
  }
}
