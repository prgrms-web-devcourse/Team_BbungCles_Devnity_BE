package com.devnity.devnity.domain.base;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(readOnly = true)
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

  @BeforeEach
  @Transactional
  void setUp() throws Exception {
    TestClass entity = new TestClass();
    em.persist(entity);
    id = entity.getId();
  }

  @Test
  @DisplayName("BaseEntity의 createdAt과 modifiedAt이 잘 생성됨")
  void findEntity() {
    TestClass testClass = em.find(TestClass.class, id);
    assertThat(testClass).isNotNull();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy LLLL dd일 hh:mm:ss");
    System.out.println("createdAt : " + testClass.getCreatedAt().format(formatter));
    System.out.println("modifiedAt : " + testClass.getModifiedAt().format(formatter));
  }

}