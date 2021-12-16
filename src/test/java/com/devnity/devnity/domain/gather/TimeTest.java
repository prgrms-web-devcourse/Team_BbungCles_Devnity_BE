package com.devnity.devnity.domain.gather;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class TimeTest {

  @Test
  void temp(){
    System.out.println(LocalDate.now());
    System.out.println(LocalDateTime.now());

    System.out.println(LocalDate.now().atTime(23, 59, 59));
    System.out.println(LocalDate.now().plusDays(1).atTime(23, 59, 59));
  }
}
