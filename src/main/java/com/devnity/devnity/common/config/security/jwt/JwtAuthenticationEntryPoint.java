package com.devnity.devnity.common.config.security.jwt;

import com.devnity.devnity.common.error.AuthErrorResponse;
import com.devnity.devnity.common.error.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
    AuthenticationException authException) throws IOException, ServletException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    try (ServletOutputStream outputStream = response.getOutputStream()) {

      String code = (String) request.getAttribute("exception");

      AuthErrorCode authErrorCode = AuthErrorCode.of(code);

      AuthErrorResponse errorResponse = AuthErrorResponse.of(authErrorCode);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(outputStream, errorResponse);
      outputStream.flush();
    }
  }
}
