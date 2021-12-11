package com.devnity.devnity.domain.user.dto.request;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.utils.annotation.Email;
import com.devnity.devnity.domain.user.utils.annotation.Password;
import com.devnity.devnity.domain.user.utils.annotation.UserName;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@Getter
public class SignUpRequest {

  @Email
  private String email;

  @UserName
  private String name;

  @Password
  private String password;

  @NotBlank
  private String course;

  @NotNull
  private UserRole role;

  @Min(1)
  private int generation;

  @Builder
  public SignUpRequest(String email, String name, String password, String course,
      UserRole role, int generation) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.course = course;
    this.role = role;
    this.generation = generation;
  }

  public User toEntity(
      PasswordEncoder passwordEncoder, Course course, Generation generation) {

    return User.builder()
        .role(role)
        .name(name)
        .generation(generation)
        .course(course)
        .email(email)
        .password(passwordEncoder.encode(password))
        .build();
  }

}


