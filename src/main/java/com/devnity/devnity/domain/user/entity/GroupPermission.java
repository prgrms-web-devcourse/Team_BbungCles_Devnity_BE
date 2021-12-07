package com.devnity.devnity.domain.user.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "group_permission")
public class GroupPermission {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;

  @ManyToOne(optional = false)
  @JoinColumn(name = "group_id", nullable = false)
  private Group group;

  public GroupPermission(Permission permission, Group group) {
    this.permission = permission;
    this.group = group;
  }
}
