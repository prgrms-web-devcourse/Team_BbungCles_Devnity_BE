package com.devnity.devnity.domain.mapgakco.repository;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapgakcoApplicantRepository extends JpaRepository<MapgakcoApplicant, Long> {

  Optional<MapgakcoApplicant> findByMapgakcoAndUser(Mapgakco mapgakco, User user);

  void deleteByMapgakcoAndUser(Mapgakco mapgakco, User user);

}
