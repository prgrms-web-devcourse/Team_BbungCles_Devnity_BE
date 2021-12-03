package com.devnity.devnity.common.config;

import com.devnity.devnity.domain.jwt.Jwt;
import com.devnity.devnity.domain.jwt.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtConfig jwtConfig;

  public WebSecurityConfig(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .anyRequest().permitAll()
          .and()
        /**
         * 사용하지 않는 Security Filter disable
         * */
        .headers()
          .disable()
        .csrf()
          .disable()
        .formLogin()
          .disable()
        .rememberMe()
          .disable()
        .logout()
          .disable()
        .httpBasic()
          .disable()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Session 사용하지 않음

        ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public Jwt jwt() {
    return new Jwt(
        jwtConfig.getIssuer(),
        jwtConfig.getClientSecret(),
        jwtConfig.getExpirySeconds());
  }
}
