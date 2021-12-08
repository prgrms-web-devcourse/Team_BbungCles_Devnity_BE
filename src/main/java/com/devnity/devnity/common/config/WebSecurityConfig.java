package com.devnity.devnity.common.config;

import com.devnity.devnity.domain.auth.jwt.Jwt;
import com.devnity.devnity.domain.auth.jwt.JwtAuthenticationFilter;
import com.devnity.devnity.domain.auth.jwt.JwtAuthenticationProvider;
import com.devnity.devnity.domain.auth.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtConfig jwtConfig;

  public WebSecurityConfig(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/docs/**", "/h2-console/**");
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    JwtAuthenticationProvider provider = getApplicationContext().getBean(
        JwtAuthenticationProvider.class);
    auth.authenticationProvider(provider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/api/v1/auth/**").permitAll()
          .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
          .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
          .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
          .anyRequest().authenticated()
          .and()
        /** 사용하지 않는 Security Filter disable
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
        /***
         * Stateless
         */
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
          .and()
        
        /**
         * JwtAuthenticationFilter 등록
         * */
        .addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class)
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

  @Bean
  public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, AuthService authService) {
    return new JwtAuthenticationProvider(jwt, authService);
  }

  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    Jwt jwt = getApplicationContext().getBean(Jwt.class);
    return new JwtAuthenticationFilter(jwtConfig.getHeader(), jwt);
  }
}

