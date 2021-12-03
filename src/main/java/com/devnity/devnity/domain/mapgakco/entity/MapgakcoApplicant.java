package com.devnity.devnity.domain.mapgakco.entity;

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
@Table(name = "mapgakco_applicant", uniqueConstraints = {
    @UniqueConstraint(
        columnNames = {"user_id", "mapgakco_id"}
    )
})
public class MapgakcoApplicant {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, name = "user_id")
  private Long userId;

  @Column(nullable = false, name = "mapgakco_id")
  private Long mapgakcoId;

  public MapgakcoApplicant(Long userId, Long mapgakcoId) {
    this.userId = userId;
    this.mapgakcoId = mapgakcoId;
  }
}
