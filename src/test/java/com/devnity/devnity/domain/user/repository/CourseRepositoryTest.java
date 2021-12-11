package com.devnity.devnity.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.setting.config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(TestConfig.class)
@DataJpaTest
class CourseRepositoryTest {

  @Autowired private CourseRepository courseRepository;

  @DisplayName("이름으로 코스를 찾을 수 있다")
  @Test 
  public void testFindByName() throws Exception {
    // given
    courseRepository.save(new Course("FE"));
    Course course = courseRepository.findByName("FE").get();
    // when

    // then
    assertThat(course.getName()).isEqualTo("FE");
  } 
  
  
}