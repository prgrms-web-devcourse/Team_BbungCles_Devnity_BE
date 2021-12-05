package com.devnity.devnity.domain.auth.service;

import com.devnity.devnity.domain.auth.dto.request.LoginRequest;
import com.devnity.devnity.domain.auth.dto.response.LoginResponse;
import com.devnity.devnity.domain.auth.entity.RefreshToken;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.auth.jwt.JwtAuthenticationToken;
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

  private final RefreshTokenService refreshTokenService;

  public LoginResponse login(LoginRequest request) {
    JwtAuthenticationToken authToken = new JwtAuthenticationToken(
        request.getEmail(), request.getPassword());

    Authentication result = authenticationManager.authenticate(authToken);

    JwtAuthenticationToken authenticated = (JwtAuthenticationToken) result;
    JwtAuthentication principal = (JwtAuthentication) authenticated.getPrincipal();
    RefreshToken refreshToken = refreshTokenService.createRefreshToken(principal.getUserId());
    User user = (User) authenticated.getDetails();

    return new LoginResponse(principal.getToken(), refreshToken.getToken(), user.getGroupName());
  }

  public User login(String principal, String credentials) {
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
