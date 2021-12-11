package com.devnity.devnity.domain.admin.controller.dto;

import com.devnity.devnity.domain.user.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
   Long id;
   String name;

   public static CourseResponse from(Course course) {
      return new CourseResponse(course.getId(), course.getName());
   }
}
