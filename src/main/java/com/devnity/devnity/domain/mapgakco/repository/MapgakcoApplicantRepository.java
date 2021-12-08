package com.devnity.devnity.domain.mapgakco.repository;

import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapgakcoApplicantRepository extends JpaRepository<MapgakcoApplicant, Long> {

    // void deleteByMapgakcoAndUser(Mapgakco mapgakco, User user);

}
