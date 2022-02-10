package com.devnity.devnity.web.introduction.dto.request;

import com.devnity.devnity.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SearchIntroductionRequest{");
    sb.append("generation=").append(generation);
    sb.append(", course='").append(course).append('\'');
    sb.append(", role=").append(role);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
