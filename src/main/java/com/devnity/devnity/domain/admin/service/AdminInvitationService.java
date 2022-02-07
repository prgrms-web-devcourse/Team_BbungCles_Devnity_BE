package com.devnity.devnity.domain.admin.service;

import com.devnity.devnity.web.error.exception.ErrorCode;
import com.devnity.devnity.web.error.exception.InvalidValueException;
import com.devnity.devnity.domain.admin.dto.InvitationDto;
import com.devnity.devnity.domain.admin.dto.request.InvitationRequest;
import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminInvitationService {

  private final InvitationRepository repository;

  @Transactional
  public String create(InvitationRequest req) {
    String course = req.getCourse();
    Integer generation = req.getGeneration();
    UserRole role = req.getRole();
    if(repository.existsByCourseAndGenerationAndRole(course, generation, role)){
      throw new InvalidValueException(
        String.format("초대링크 중복 (course : %s, generation : %d, role : %s)", course, generation, role),
        ErrorCode.DUPLICATED_LINK
      );
    }
    Invitation invitation = repository.save(new Invitation(course, generation, role, req.getDeadline()));
    return invitation.getUuid();
  }

  @Transactional
  public String delete(String uuid){
    repository.deleteByUuid(uuid);
    return "delete success";
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
