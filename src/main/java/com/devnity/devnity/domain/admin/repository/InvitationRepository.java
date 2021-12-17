package com.devnity.devnity.domain.admin.repository;

import com.devnity.devnity.domain.admin.entity.Invitation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

  Optional<Invitation> findByUuid(UUID uuid);

  @Query("SELECT i FROM Invitation AS i WHERE CURRENT_TIMESTAMP > i.deadline.deadline")
  List<Invitation> findExpiredInvitation();

}