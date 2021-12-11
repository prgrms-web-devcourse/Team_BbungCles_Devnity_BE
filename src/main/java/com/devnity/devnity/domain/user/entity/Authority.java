package com.devnity.devnity.domain.user.entity;

import com.devnity.devnity.domain.user.service.UserServiceUtils;
import com.mysema.commons.lang.Assert;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Authority {
  USER("ROLE_USER", "사용자"),
  ADMIN("ROLE_ADMIN", "관리자");

  private final String role;
  private final String description;

  public static Authority of(UserRole userRole) {

    UserServiceUtils.notNull(userRole, "UserRole must be provided");

    if (userRole.equals(UserRole.MANAGER))
      return ADMIN;
    return USER;
  }
}
