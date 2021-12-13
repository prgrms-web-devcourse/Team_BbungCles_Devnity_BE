package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GatherApplicantRepository extends JpaRepository<GatherApplicant, Long> {

  boolean existsByUserAndGather(User user, Gather gather);

//  Optional<GatherApplicant> findByUserAndGather(User user, Gather gather);

//  Long countByGather(Gather gather);

//  @Query("SELECT ga FROM GatherApplicant AS ga WHERE ga.user.id=:userId AND ga.gather.id=:gatherId")
  Optional<GatherApplicant> findByUserIdAndGatherId(Long userId, Long gatherId);

}
