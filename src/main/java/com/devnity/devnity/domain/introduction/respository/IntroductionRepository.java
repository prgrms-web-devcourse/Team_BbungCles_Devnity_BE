package com.devnity.devnity.domain.introduction.respository;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.custom.IntroductionCustomRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IntroductionRepository extends JpaRepository<Introduction, Long>,
  IntroductionCustomRepository {

  @Query("select i from Introduction i join fetch i.user where i.id = :id")
  Optional<Introduction> findById(@Param("id") Long id);
}
