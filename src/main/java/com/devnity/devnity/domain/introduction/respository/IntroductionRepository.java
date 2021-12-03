package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

}
