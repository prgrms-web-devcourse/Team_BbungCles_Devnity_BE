package com.devnity.devnity.domain.admin.controller.dto;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.user.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

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
