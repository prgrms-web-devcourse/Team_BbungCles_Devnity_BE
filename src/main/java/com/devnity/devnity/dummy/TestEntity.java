package com.devnity.devnity.dummy;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "test")
@Entity
public class TestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String str;

  @Builder
  public TestEntity(Long id, String str) {
    this.id = id;
    this.str = str;
  }
}
