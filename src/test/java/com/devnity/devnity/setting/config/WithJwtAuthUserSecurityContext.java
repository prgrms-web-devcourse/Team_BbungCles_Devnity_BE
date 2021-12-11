package com.devnity.devnity.setting.config;

import com.devnity.devnity.domain.auth.jwt.Jwt;
import com.devnity.devnity.domain.auth.jwt.Jwt.Claims;
import com.devnity.devnity.domain.auth.jwt.JwtAuthentication;
import com.devnity.devnity.domain.auth.jwt.JwtAuthenticationToken;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.CourseRepository;
import com.devnity.devnity.domain.user.repository.GenerationRepository;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.setting.annotation.WithJwtAuthUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithJwtAuthUserSecurityContext implements WithSecurityContextFactory<WithJwtAuthUser> {

  private final Jwt jwt;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final GenerationRepository generationRepository;

  public WithJwtAuthUserSecurityContext(
      Jwt jwt,
      UserRepository userRepository,
      CourseRepository courseRepository,
      GenerationRepository generationRepository) {

    this.jwt = jwt;
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.generationRepository = generationRepository;
  }

  @Override
  public SecurityContext createSecurityContext(WithJwtAuthUser withJwtAuthUser) {
    String email = withJwtAuthUser.email();
    UserRole role = withJwtAuthUser.role();
    String courseName = withJwtAuthUser.course();
    String name = withJwtAuthUser.name();
    int sequence = withJwtAuthUser.generation();
    String password = withJwtAuthUser.password();

    Generation generation = generationRepository.findBySequence(withJwtAuthUser.generation())
      .orElseGet(() -> generationRepository.save(new Generation(sequence)));

    Course course = courseRepository.findByName(courseName)
      .orElseGet(() -> courseRepository.save(new Course(courseName)));

    User user = userRepository.save(User.builder()
      .email(email)
      .name(name)
      .role(role)
      .password(password)
      .generation(generation)
      .course(course)
      .build());

    String jwtToken = jwt.sign(Claims.from(user.getId(), email, role.toString()));

    JwtAuthentication authentication = new JwtAuthentication(jwtToken, user.getId(), email);
    JwtAuthenticationToken jwtAuthenticationToken =
        new JwtAuthenticationToken(
            authentication,
            user.getPassword(),
            new SimpleGrantedAuthority(role.toString()));

    SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);
    return SecurityContextHolder.getContext();
  }

}
