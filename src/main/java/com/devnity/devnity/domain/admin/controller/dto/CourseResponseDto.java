package com.devnity.devnity.domain.admin.controller.dto;

import com.devnity.devnity.domain.user.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {
   Long id;
   String name;

   public static CourseResponseDto from(Course course) {
      return new CourseResponseDto(course.getId(), course.getName());
   }
}
