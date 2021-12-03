package com.devnity.devnity.introduction.domain;

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
        columnNames = {"introduction_id", "user_id"}
    )
})
public class IntroductionLike {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "introduction_id")
  private Long introductionId;

  @Column(nullable = false, name = "user_id")
  private Long userId;

  public IntroductionLike(Long introductionId, Long userId) {
    this.introductionId = introductionId;
    this.userId = userId;
  }
}
