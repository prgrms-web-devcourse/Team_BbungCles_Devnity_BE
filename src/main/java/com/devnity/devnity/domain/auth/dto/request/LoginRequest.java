package com.devnity.devnity.domain.auth.dto.request;

import com.devnity.devnity.domain.user.utils.annotation.Password;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginRequest {

  @Email private String email;

  @Password private String password;

  public LoginRequest(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("LoginRequest{");
    sb.append("principal='").append(email).append('\'');
    sb.append(", credentials='").append(password).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
