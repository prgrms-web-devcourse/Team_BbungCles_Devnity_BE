package com.devnity.devnity.mogakco.domain;

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
@Table(name = "mogakco_applicant", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"user_id", "mogakco_id"}
    )
})
public class MogakcoApplicant {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "user_id")
  private Long userId;

  @Column(nullable = false, name = "mogakco_id")
  private Long mogakcoId;

  public MogakcoApplicant(Long userId, Long mogakcoId) {
    this.userId = userId;
    this.mogakcoId = mogakcoId;
  }
}
