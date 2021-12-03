package com.devnity.devnity.domain.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class Jwt {

  private final String issuer;

  private final String clientSecret;

  private final int expirySeconds;

  private final Algorithm algorithm;

  private final JWTVerifier jwtVerifier;

  public Jwt(String issuer, String clientSecret, int expirySeconds) {
    this.issuer = issuer;
    this.clientSecret = clientSecret;
    this.expirySeconds = expirySeconds;
    this.algorithm = Algorithm.HMAC512(clientSecret);
    this.jwtVerifier = com.auth0.jwt.JWT.require(algorithm).withIssuer(issuer).build();
  }

  public String sign(Claims claims) {
    Date now = new Date();

    JWTCreator.Builder builder = com.auth0.jwt.JWT.create();

    builder.withIssuer(issuer);
    builder.withIssuedAt(now);

    if (expirySeconds > 0) {
      builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
    }
    builder.withClaim("userId", claims.userId);
    builder.withClaim("email", claims.email);
    builder.withArrayClaim("roles", claims.roles);

    return builder.sign(algorithm);
  }

  public Claims verity(String token) {
    return new Claims(jwtVerifier.verify(token));
  }

  @NoArgsConstructor(access = AccessLevel.PRIVATE)
  public static class Claims {
    Long userId;
    String email;
    String[] roles;
    Date iat;
    Date exp;

    public Claims(DecodedJWT decodedJWT) {
      Claim userId = decodedJWT.getClaim("userId");

      if (!userId.isNull()) {
        this.userId = userId.asLong();
      }

      Claim email = decodedJWT.getClaim("email");

      if (!email.isNull()) {
        this.email = email.asString();
      }

      Claim roles = decodedJWT.getClaim("roles");

      if (!roles.isNull()) {
        this.roles = roles.asArray(String.class);
      }

      this.iat = decodedJWT.getIssuedAt();
      this.exp = decodedJWT.getExpiresAt();
    }

    public static Claims from(Long userId, String email, String[] roles) {
      Claims claims = new Claims();
      claims.userId = userId;
      claims.email = email;
      claims.roles = roles;
      return claims;
    }

    public Map<String, Object> asMap() {
      return Map.of("userId", this.userId,
            "email", email,
            "roles", roles,
            "iat", iat,
            "exp", exp);
    }
  }
}
