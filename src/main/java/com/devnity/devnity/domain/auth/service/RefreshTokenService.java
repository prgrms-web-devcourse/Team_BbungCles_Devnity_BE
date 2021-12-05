package com.devnity.devnity.domain.auth.service;

import com.devnity.devnity.domain.auth.dto.request.TokenRefreshRequest;
import com.devnity.devnity.domain.auth.dto.response.TokenRefreshResponse;
import com.devnity.devnity.domain.auth.entity.RefreshToken;
import com.devnity.devnity.domain.auth.jwt.Jwt;
import com.devnity.devnity.domain.auth.jwt.Jwt.Claims;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.auth.repository.RefreshTokenRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RefreshTokenService {

  @Value("#{'${refresh-token.expiry-seconds}'}")
  private int refreshTokenExpirySeconds;

  private final RefreshTokenRepository refreshTokenRepository;

  private final UserRepository userRepository;

  private final Jwt jwt;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  @Transactional
  public RefreshToken createRefreshToken(Long userId) {

    User user = getUser(userId);
    Date now = new Date();
    String token = UUID.randomUUID().toString();

    RefreshToken refreshToken = RefreshToken.builder()
        .user(user)
        .token(token)
        .expiryDate(new Date(now.getTime() + refreshTokenExpirySeconds * 1_000L))
        .build();

    refreshTokenRepository.save(refreshToken);

    return refreshToken;
  }

  public TokenRefreshResponse refresh(TokenRefreshRequest request) {
    RefreshToken refreshToken = refreshTokenRepository
        .findByToken(request.getRefreshToken())
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format("There is no refresh token for %s", request.getRefreshToken())));

    verify(refreshToken);
    String token = createJwtToken(refreshToken);

    return new TokenRefreshResponse(token, request.getRefreshToken());
  }

  private String createJwtToken(RefreshToken refreshToken) {
    User user = refreshToken.getUser();
    String[] roles = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toArray(String[]::new);

    Jwt.Claims claims = Claims.from(user.getId(), user.getEmail(), roles);

    return jwt.sign(claims);
  }

  @Transactional
  public void verify(RefreshToken token) {
    if (token.isExpired(new Date())) {
      refreshTokenRepository.delete(token);
      throw new IllegalArgumentException(String.format("Refresh Token was expired. %s", token));
    }
  }

  @Transactional
  public void deleteByUserId(Long userId) {
    refreshTokenRepository.deleteByUserId(userId);
  }

  private User getUser(Long userId) {
    return userRepository
        .findById(userId)
        .orElseThrow(
            () ->
                new IllegalArgumentException(String.format("There is no user for id=%d", userId)));
  }
}
