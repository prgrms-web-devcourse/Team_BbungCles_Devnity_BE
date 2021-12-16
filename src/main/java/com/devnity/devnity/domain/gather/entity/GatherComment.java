package com.devnity.devnity.domain.gather.entity;

import com.devnity.devnity.domain.base.BaseEntity;
import com.devnity.devnity.domain.gather.entity.category.GatherCommentStatus;
import com.devnity.devnity.domain.user.entity.User;
import java.util.Objects;
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
public class GatherComment extends BaseEntity {

  private static final String DELETED_CONTENT = "[삭제된 댓글입니다]";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String content;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private GatherCommentStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id", referencedColumnName = "id")
  private GatherComment parent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "gather_id", referencedColumnName = "id", nullable = false)
  private Gather gather;

  @Builder
  public GatherComment(String content, GatherComment parent, User user, Gather gather) {
    this.content = content;
    this.parent = parent;
    this.user = user;
    this.gather = gather;

    this.status = GatherCommentStatus.POSTED;
  }

  public String getContent() {
    if (this.status == GatherCommentStatus.DELETED) {
      return DELETED_CONTENT;
    }
    return this.content;
  }

// ---------------------------- ( 비즈니스 메소드 ) ----------------------------

  public boolean isWrittenBy(Long userId) {
    return this.user.getId().equals(userId);
  }

  public void update(String content) {
    this.content = content;
  }

  public void delete(){
    this.status = GatherCommentStatus.DELETED;
  }



}
