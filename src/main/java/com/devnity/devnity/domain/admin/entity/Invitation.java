package com.devnity.devnity.domain.admin.entity;

import com.devnity.devnity.domain.base.BaseEntity;
import com.devnity.devnity.domain.base.vo.Deadline;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "invitation", uniqueConstraints = {
  @UniqueConstraint(
    columnNames = {"course", "generation", "role"}
  )
})
public class Invitation extends BaseEntity {

  @Id
  @Column(name = "uuid", nullable = false, length = 30)
  private UUID uuid;

  @Column(name = "course", nullable = false, length = 20)
  private String course;

  @Column(name = "generation", nullable = false)
  private Integer generation;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 20)
  private UserRole role;

  @Embedded
  private Deadline deadline;

  @Builder
  public Invitation(String course, Integer generation, UserRole role, LocalDate deadline) {
    this.uuid = UUID.randomUUID();
    this.course = course;
    this.generation = generation;
    this.role = role;
    this.deadline = new Deadline(deadline);
  }

}
