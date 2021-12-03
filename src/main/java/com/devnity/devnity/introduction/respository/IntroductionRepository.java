package com.devnity.devnity.introduction.respository;

import com.devnity.devnity.introduction.domain.Introduction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroductionRepository extends JpaRepository<Introduction, Long> {

}
