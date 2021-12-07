package com.devnity.devnity.common.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BaseEntityTest {

  @Autowired
  EntityManager em;

  @Entity
  static class TestClass extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
      return id;
    }
  }

  private Long id;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @BeforeEach
  void setUp() throws Exception {
    TestClass entity = new TestClass();
    em.persist(entity);
    Long id = entity.getId();
    createdAt = entity.getCreatedAt();
    modifiedAt = entity.getModifiedAt();
  }

  @Test
  @DisplayName("BaseEntity의 createdAt과 modifiedAt이 잘 생성됨")
  void findEntity() {
    TestClass testClass = em.find(TestClass.class, id);
    assertThat(testClass.getCreatedAt()).isEqualTo(createdAt);
    assertThat(testClass.getModifiedAt()).isEqualTo(modifiedAt);
  }

}