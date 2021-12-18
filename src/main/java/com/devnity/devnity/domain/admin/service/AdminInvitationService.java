package com.devnity.devnity.domain.admin.service;

import com.devnity.devnity.domain.admin.dto.InvitationDto;
import com.devnity.devnity.domain.admin.dto.request.InvitationRequest;
import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminInvitationService {

  private final InvitationRepository repository;

  @Transactional
  public UUID create(InvitationRequest req) {
    Invitation invitation = new Invitation(req.getCourse(), req.getGeneration(), req.getRole(), req.getDeadline());
    repository.save(invitation);
    return invitation.getUuid();
  }

  public List<InvitationDto> getAll(){
    return repository.findAll().stream()
      .map(InvitationDto::of)
      .collect(Collectors.toList());
  }

  @Transactional
  public List<Invitation> deleteExpiredInvitation(){
    List<Invitation> expiredInvitations = repository.findExpiredInvitation();
    repository.deleteAll(expiredInvitations);
    return expiredInvitations;
  }

}
