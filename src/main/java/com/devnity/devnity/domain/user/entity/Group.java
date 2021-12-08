package com.devnity.devnity.domain.user.entity;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "group_s")
public class Group {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", unique = true, nullable = false, length = 20)
  private String name;

  @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
  private List<GroupPermission> permissions = new ArrayList<>();

  public Group(String name) {
    this.name = name;
  }

  List<GrantedAuthority> getAuthorities() {
    return permissions.stream()
        .map(gp -> new SimpleGrantedAuthority(gp.getPermission().getName()))
        .collect(toList());
  }
}
