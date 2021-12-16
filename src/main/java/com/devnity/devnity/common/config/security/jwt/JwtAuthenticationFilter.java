package com.devnity.devnity.common.config.security.jwt;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.devnity.devnity.common.error.exception.AuthErrorCode;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Objects;
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
          GrantedAuthority authority = getAuthorities(claims);

          if (Objects.nonNull(email) && !email.isEmpty() && userId != null && authority != null) {
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                new JwtAuthentication(token, userId, email), null, authority);

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          }
        } catch (TokenExpiredException tokenExpiredException){
          request.setAttribute("exception", AuthErrorCode.TOKEN_EXPIRED.getCode());
        } catch (Exception e) {
          request.setAttribute("exception", AuthErrorCode.INVALID_TOKEN.getCode());
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

  private GrantedAuthority getAuthorities(Jwt.Claims claims) {
    String role = claims.role;
    return role == null
        ? null
        : new SimpleGrantedAuthority(role);
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
