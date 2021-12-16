package com.devnity.devnity.domain.user.service;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import javax.persistence.EntityManager;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@Getter
public class IntroDto {

  private String content;
  private String userEmail;
  private Long userId;

  @Builder
  public IntroDto(String content, String userEmail, Long userId) {
    this.content = content;
    this.userEmail = userEmail;
    this.userId = userId;
  }

  public static IntroDto of(Introduction introduction,
    EntityManager em) {
    System.out.println("introduction.getUser() = " + introduction.getUser());
    return IntroDto.builder()
        .content(introduction.getDescription())
        //        .userEmail(introduction.getUser().getEmail())
        .userId(introduction.getUser().getId())
        .build();
  }
}
