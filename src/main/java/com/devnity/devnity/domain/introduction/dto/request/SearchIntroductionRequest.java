package com.devnity.devnity.domain.introduction.dto.request;

import com.devnity.devnity.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SearchIntroductionRequest {

  private Integer generation;
  private String course;
  private UserRole role;
  private String name;

  @Builder
  public SearchIntroductionRequest(Integer generation, String course,
    UserRole role, String name) {
    this.generation = generation;
    this.course = course;
    this.role = role;
    this.name = name;
  }
}
