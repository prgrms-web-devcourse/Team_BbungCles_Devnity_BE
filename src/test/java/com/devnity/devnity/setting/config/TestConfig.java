package com.devnity.devnity.setting.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

  @Autowired private EntityManager entityManager;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }
}
