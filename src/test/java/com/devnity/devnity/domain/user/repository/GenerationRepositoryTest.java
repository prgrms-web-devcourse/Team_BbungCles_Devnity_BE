package com.devnity.devnity.domain.user.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.devnity.devnity.domain.user.entity.Generation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class GenerationRepositoryTest {

  @Autowired private GenerationRepository generationRepository;

  @DisplayName("Sequence로 Generation을 찾을 수 있다")
  @Test
  public void testFindBySequence() throws Exception {
    // given
    Generation generation = new Generation(100);
    generationRepository.save(generation);

    // when
    Generation found = generationRepository.findBySequence(generation.getSequence());

    // then
    assertThat(found.getSequence()).isEqualTo(generation.getSequence());
  }



}