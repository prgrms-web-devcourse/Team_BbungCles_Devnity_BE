package com.devnity.devnity.domain.auth;

import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final Jwt jwt;

  private final UserService userService;

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
      User user = userService.login(principal, credential);
      List<GrantedAuthority> authorities = user.getAuthorities();

      String token = getToken(user.getId(), user.getEmail(), authorities);

      JwtAuthenticationToken authenticated = new JwtAuthenticationToken(
          new JwtAuthentication(token, user.getId(), user.getEmail()), null, authorities);

      authenticated.setDetails(user);
      
      return authenticated;
    } catch (IllegalArgumentException e) {
      throw new BadCredentialsException(e.getMessage());
    } catch (Exception e) {
      throw new AuthenticationServiceException(e.getMessage());
    }
  }

  private String getToken(Long id, String email, List<GrantedAuthority> authorities) {
    String[] roles = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .toArray(String[]::new);

    return jwt.sign(Jwt.Claims.from(id, email, roles));
  }

}
