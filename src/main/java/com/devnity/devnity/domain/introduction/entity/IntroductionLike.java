package com.devnity.devnity.domain.introduction.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "introduction_like", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"user_id", "introduction_id"}
    )
})
public class IntroductionLike {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "user_id")
  private Long userId;

  @Column(nullable = false, name = "introduction_id")
  private Long introductionId;

  public IntroductionLike(Long userId, Long introductionId) {
    this.userId = userId;
    this.introductionId = introductionId;
  }
}
