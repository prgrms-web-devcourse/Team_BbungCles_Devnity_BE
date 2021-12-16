package com.devnity.devnity.domain.gather.entity.category;

import java.util.List;
import lombok.Getter;

@Getter
public enum GatherStatus {
  GATHERING,
  CLOSED,
  FULL,
  DELETED;

  public static List<GatherStatus> available() {
    return List.of(GatherStatus.GATHERING);
  }

  public static List<GatherStatus> unavailable() {
    return List.of(GatherStatus.CLOSED, GatherStatus.FULL);
  }
}
