package com.devnity.devnity.domain.gather.entity;

import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.user.entity.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "gather_comment")
public class GatherComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String content;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherCommentStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)
  private GatherComment parent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private Gather gather;

  @Builder
  public GatherComment(Long id, String content, GatherCommentStatus status, GatherComment parent, User user, Gather gather) {
    this.id = id;
    this.content = content;
    this.status = status;
    this.parent = parent;
    this.user = user;
    this.gather = gather;
  }
}
