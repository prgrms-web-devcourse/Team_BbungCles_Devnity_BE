package com.devnity.devnity.domain.admin.controller.dto;

import com.devnity.devnity.domain.user.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    Long id;
    String name;

    public CourseRequestDto(String name) {
        this.name = name;
    }

    public Course to() {
        return new Course(name);
    }
}
