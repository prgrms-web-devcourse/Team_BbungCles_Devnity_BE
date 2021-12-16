package com.devnity.devnity.common.config.security.jwt;

import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.auth.service.AuthService;
import com.devnity.devnity.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final Jwt jwt;

  private final AuthService authService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;

    return processUserAuthentication(
        String.valueOf(jwtAuthenticationToken.getPrincipal()), jwtAuthenticationToken.getCredentials());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }

  private Authentication processUserAuthentication(String principal, String credential) {
    try {
      User user = authService.authenticate(principal, credential);
      GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthority().getRole());

      String token = getToken(user.getId(), user.getEmail(), authority);

      JwtAuthenticationToken authenticated = new JwtAuthenticationToken(
          new JwtAuthentication(token, user.getId(), user.getEmail()), null, authority);

      authenticated.setDetails(user);
      
      return authenticated;
    } catch (InvalidValueException e) {
      throw new BadCredentialsException(e.getMessage());
    } catch (Exception e) {
      throw new AuthenticationServiceException(e.getMessage());
    }
  }

  private String getToken(Long id, String email, GrantedAuthority authority) {
    String role = authority.getAuthority();

    return jwt.sign(Jwt.Claims.from(id, email, role));
  }

}
