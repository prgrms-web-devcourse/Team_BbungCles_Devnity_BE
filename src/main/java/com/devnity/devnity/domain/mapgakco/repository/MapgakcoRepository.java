package com.devnity.devnity.domain.mapgakco.repository;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapgakcoRepository extends JpaRepository<Mapgakco, Long>, MapgakcoCustomRepository {

  Optional<Mapgakco> findByIdAndStatusNot(Long id, MapgakcoStatus status);

  List<Mapgakco> getByStatusNot(MapgakcoStatus status);

}
