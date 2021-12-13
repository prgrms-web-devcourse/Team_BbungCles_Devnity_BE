package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatherRepository extends JpaRepository<Gather, Long>, GatherCustomRepository{

}
