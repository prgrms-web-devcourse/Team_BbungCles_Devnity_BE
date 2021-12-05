package com.devnity.devnity.domain.gather.entity;

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
@Table(name = "gather_applicant", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"user_id", "gather_id"}
    )
})
public class GatherApplicant {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "gather_id", nullable = false)
  private Long gatherId;

  public GatherApplicant(Long userId, Long gatherId) {
    this.userId = userId;
    this.gatherId = gatherId;
  }
}
