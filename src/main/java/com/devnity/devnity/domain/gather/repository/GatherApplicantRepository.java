package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatherApplicantRepository extends JpaRepository<GatherApplicant, Long> {

  boolean existsByUserAndGather(User user, Gather gather);

  Optional<GatherApplicant> findByUserAndGather(User user, Gather gather);

}
