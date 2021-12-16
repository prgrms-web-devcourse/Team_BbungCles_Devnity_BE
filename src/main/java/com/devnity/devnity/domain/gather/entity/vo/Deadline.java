package com.devnity.devnity.domain.gather.entity.vo;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class Deadline {

  @Column(nullable = false)
  private LocalDateTime deadline;

  public Deadline(LocalDate date) {
    if (isInValid(date)) {
      throw new InvalidValueException(
        String.format("잘못된 deadline 요청 (date : %s)", date),
        ErrorCode.INVALID_DEADLINE
      );
    }
    this.deadline = date.atTime(23, 59, 59);
  }

  private boolean isInValid(LocalDate date) {
    return date.isBefore(LocalDate.now());
  }

}
