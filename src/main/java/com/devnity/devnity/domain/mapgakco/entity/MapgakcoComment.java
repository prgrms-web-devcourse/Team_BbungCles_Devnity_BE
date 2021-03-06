package com.devnity.devnity.domain.mapgakco.entity;

import com.devnity.devnity.domain.base.BaseEntity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "mapgakco_comment")
public class MapgakcoComment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 300)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "mapgakco_id")
  private Mapgakco mapgakco;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private MapgakcoComment parent;

  @Column(nullable = false, length = 10)
  @Enumerated(EnumType.STRING)
  private MapgakcoCommentStatus status;

  @Builder
  public MapgakcoComment(String content, User user, Mapgakco mapgakco, MapgakcoComment parent) {
    this.content = content;
    this.user = user;
    this.mapgakco = mapgakco;
    this.parent = parent;
    this.status = MapgakcoCommentStatus.POSTED;
  }

  public void update(String content) {
    this.content = content;
  }

  public void delete() {
    this.status = MapgakcoCommentStatus.DELETED;
  }
}
