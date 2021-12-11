package com.devnity.devnity.domain.auth.service;

import com.devnity.devnity.domain.auth.dto.request.LoginRequest;
import com.devnity.devnity.domain.auth.dto.response.LoginResponse;
import com.devnity.devnity.common.config.security.jwt.JwtAuthentication;
import com.devnity.devnity.common.config.security.jwt.JwtAuthenticationToken;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AuthService {
  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  public LoginResponse login(LoginRequest request) {
    JwtAuthenticationToken authToken = new JwtAuthenticationToken(
        request.getEmail(), request.getPassword());

    Authentication result = authenticationManager.authenticate(authToken);

    JwtAuthenticationToken authenticated = (JwtAuthenticationToken) result;
    JwtAuthentication principal = (JwtAuthentication) authenticated.getPrincipal();
    User user = (User) authenticated.getDetails();

    return new LoginResponse(principal.getToken(), user.getAuthority());
  }

  public User authenticate(String principal, String credentials) {
    User user = userRepository
        .findUserByEmail(principal)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("Could not found user for email=%s", principal)));

    user.checkPassword(passwordEncoder, credentials);
    return user;
  }
}
