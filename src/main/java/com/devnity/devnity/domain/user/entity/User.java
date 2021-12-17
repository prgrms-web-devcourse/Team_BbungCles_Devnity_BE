package com.devnity.devnity.domain.user.entity;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import com.devnity.devnity.domain.base.BaseEntity;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"email"}
    )
})
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", unique = true, nullable = false, length = 200)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 50)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private UserRole role;

  @OneToOne
  @JoinColumn(name = "generation_id", nullable = false)
  private Generation generation;

  @OneToOne
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @OneToOne(fetch = FetchType.LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
  private Introduction introduction;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Authority authority;

  @Builder
  public User(String email, String password, String name, UserRole role,
      Generation generation, Course course) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
    this.authority = Authority.of(role);
    this.generation = generation;
    this.course = course;
    this.status = UserStatus.ACTIVE;
    this.introduction = new Introduction(this);
  }

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  public String getCourseName() {
    return this.course.getName();
  }

  public int getGenerationSequence() {
    return this.generation.getSequence();
  }

  //== 비즈니스 메서드 ==//
  public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
    if (!passwordEncoder.matches(credentials, password)) {
      throw new BadCredentialsException("Bad Credential!");
    }
  }
}
