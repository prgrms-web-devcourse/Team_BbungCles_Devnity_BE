package com.devnity.devnity.domain.auth;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final String headerKey;

  private final Jwt jwt;


  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;

    if (SecurityContextHolder.getContext().getAuthentication() == null) {
      String token = getToken(request);
      if (Objects.nonNull(token)) {
        try {
          Jwt.Claims claims = verify(token);
          log.debug("Jwt parse result: {}", claims);

          String email = claims.email;
          Long userId = claims.userId;
          List<GrantedAuthority> authorities = getAuthorities(claims);

          if (Objects.nonNull(email) && !email.isEmpty() && userId != null && !authorities.isEmpty()) {
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                new JwtAuthentication(token, userId, email), null, authorities);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        } catch (Exception e) {
          log.warn("Jwt processing failed: {}", e.getMessage());
        }
      }
    } else {
      log.debug(
          "SecurityContextHolder not populated with security token, as it already contained: '{}'",
          SecurityContextHolder.getContext().getAuthentication());
    }

    chain.doFilter(request, response);
  }

  private List<GrantedAuthority> getAuthorities(Jwt.Claims claims) {
    String[] roles = claims.roles;
    return roles == null || roles.length == 0
        ? Collections.emptyList()
        : Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  private String getToken(HttpServletRequest request) {
    String token = request.getHeader(headerKey);

    if(Objects.nonNull(token) && !token.isEmpty()) {
      log.debug("Jwt token detected: {}", token);
      try {
        return URLDecoder.decode(token, "UTF-8");
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
    return null;
  }

  private Jwt.Claims verify(String token) {
    return jwt.verity(token);
  }
}
