package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IntroductionRepository extends JpaRepository<Introduction, Long>, IntroductionCustomRepository {

}
