package com.devnity.devnity.domain.user.entity;

import com.devnity.devnity.domain.base.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "generation")
public class Generation extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "sequence", unique = true, nullable = false)
  private int sequence;

  public Generation(int sequence) {
    this.sequence = sequence;
  }

  public void updateSequence(int sequence) {
    this.sequence = sequence;
  }
}
