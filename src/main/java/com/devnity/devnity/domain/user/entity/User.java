package com.devnity.devnity.domain.user.entity;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import java.util.List;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"email"}
    )
})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", unique = true, nullable = false, length = 254)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, length = 20)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private UserRole role;

  @OneToOne
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  @OneToOne
  @JoinColumn(name = "generation_id", nullable = false)
  private Generation generation;

  @OneToOne
  @JoinColumn(name = "course_id", nullable = false)
  private Course course;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Introduction introduction;

  @Builder
  public User(String email, String password, String name, UserRole role,
      Group group, Generation generation, Course course) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.role = role;
    this.group = group;
    this.generation = generation;
    this.course = course;
    this.status = UserStatus.JOIN;
    this.introduction = new Introduction(this);
  }

  @Enumerated(EnumType.STRING)
  private UserStatus status;

  public List<GrantedAuthority> getAuthorities() {
    return this.getGroup().getAuthorities();
  }

  public String getCourse() {
    return this.course.getName();
  }

  public int getGeneration() {
    return this.generation.getSequence();
  }

  public String getGroupName() {
    return group.getName();
  }

  //== 비즈니스 메서드 ==//
  public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
    if (!passwordEncoder.matches(credentials, password)) {
      throw new IllegalArgumentException("Bad credentials!");
    }
  }
}
