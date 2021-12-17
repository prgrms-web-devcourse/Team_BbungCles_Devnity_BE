package com.devnity.devnity.domain.admin.repository;

import com.devnity.devnity.domain.admin.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

  Optional<Invitation> findByUuid(UUID uuid);
}
