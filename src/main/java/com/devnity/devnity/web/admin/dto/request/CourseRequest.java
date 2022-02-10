package com.devnity.devnity.web.admin.dto.request;

import com.devnity.devnity.domain.user.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequest {

  Long id;
  String name;

  public CourseRequest(String name) {
    this.name = name;
  }

  public Course to() {
    return new Course(name);
  }
}
