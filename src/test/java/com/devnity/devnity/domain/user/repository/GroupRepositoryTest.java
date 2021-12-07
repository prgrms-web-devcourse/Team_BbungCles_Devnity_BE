package com.devnity.devnity.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.devnity.devnity.domain.user.entity.Group;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class GroupRepositoryTest {

  @Autowired private GroupRepository groupRepository;
  
  @DisplayName("권한 그룹 이름으로 그룹을 찾을 수 있다")
  @Test 
  public void testFindByName() throws Exception {
    // given
    Group group = groupRepository.findByName("USER_GROUP");
    // when

    // then
    assertThat(group.getName()).isEqualTo("USER_GROUP");
  } 
  
  
}