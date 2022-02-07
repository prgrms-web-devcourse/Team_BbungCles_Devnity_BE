package com.devnity.devnity.web.error;

import com.devnity.devnity.web.error.exception.AuthErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(
      HttpServletRequest request,
      HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException, ServletException {

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.SC_FORBIDDEN);

    try(ServletOutputStream outputStream = response.getOutputStream()){
      AuthErrorResponse errorResponse = AuthErrorResponse.of(AuthErrorCode.ACCESS_DENIED);
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.writeValue(outputStream, errorResponse);
      outputStream.flush();
    }
  }
}
