package com.devnity.devnity.domain.user.entity;

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

  @Column(nullable = false, length = 10)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 10)
  private UserRole role;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id")
  private Group group;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "generation_id")
  private Generation generation;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "course_id")
  private Course course;

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
  }

  @Enumerated(EnumType.STRING)
  private UserStatus status;
}
