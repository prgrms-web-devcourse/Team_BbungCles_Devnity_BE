package com.devnity.devnity.domain.mapgakco.repository;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapgakcoRepository extends JpaRepository<Mapgakco, Long>, MapgakcoCustomRepository {

}
