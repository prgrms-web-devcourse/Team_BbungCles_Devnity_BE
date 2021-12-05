package com.devnity.devnity.domain.auth.service;

import com.devnity.devnity.domain.auth.entity.RefreshToken;
import com.devnity.devnity.domain.auth.repository.RefreshTokenRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

  @Transactional
  public RefreshToken verify(RefreshToken token) {
    if (token.isExpired(new Date())) {
      refreshTokenRepository.delete(token);
      throw new IllegalArgumentException(String.format("Refresh Token was expired. %s", token));
    }

    return token;
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
