package com.devnity.devnity.common.config.security;

import com.devnity.devnity.common.config.security.cors.CorsConfig;
import com.devnity.devnity.common.config.security.jwt.Jwt;
import com.devnity.devnity.common.config.security.jwt.JwtAuthenticationEntryPoint;
import com.devnity.devnity.common.config.security.jwt.JwtAuthenticationFilter;
import com.devnity.devnity.common.config.security.jwt.JwtAuthenticationProvider;
import com.devnity.devnity.common.config.security.jwt.JwtConfig;
import com.devnity.devnity.common.error.JwtAccessDeniedHandler;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final CorsConfig corsConfig;
  private final JwtConfig jwtConfig;

  public WebSecurityConfig(CorsConfig corsConfig, JwtConfig jwtConfig) {
    this.corsConfig = corsConfig;
    this.jwtConfig = jwtConfig;
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/docs/**", "/h2-console/**", "/health/**");
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
      .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
      .antMatchers("/api/v1/auth/**").permitAll()
      .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
      .antMatchers(HttpMethod.POST, "/api/v1/users/check").permitAll()
      .antMatchers(HttpMethod.POST, "/api/v1/admin/check").permitAll()
      .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
      .anyRequest().authenticated()
      .and()
      .exceptionHandling()
      .authenticationEntryPoint(authenticationEntryPoint())
      .accessDeniedHandler(accessDeniedHandler())
      .and()
      .cors()
      .configurationSource(corsConfigurationSource())
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

  private AccessDeniedHandler accessDeniedHandler() {
    return new JwtAccessDeniedHandler();
  }

  private AuthenticationEntryPoint authenticationEntryPoint() {
    return new JwtAuthenticationEntryPoint();
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

  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    for (String origin : corsConfig.getOrigins()) {
      configuration.addAllowedOrigin(origin);
    }
    configuration.addAllowedMethod("*");
    configuration.addAllowedHeader("*");
    configuration.setAllowCredentials(false);
    configuration.setMaxAge(3600L);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}

