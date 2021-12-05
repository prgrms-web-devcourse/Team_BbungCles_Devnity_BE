package com.devnity.devnity.domain.gather.entity;

import javax.persistence.*;

import com.devnity.devnity.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
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
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gather_id", referencedColumnName = "id", nullable = false)
  private Gather gather;
  
  @Builder
  public GatherApplicant(Long id, User user, Gather gather) {
    this.id = id;
    this.user = user;
    this.gather = gather;
  }
}
