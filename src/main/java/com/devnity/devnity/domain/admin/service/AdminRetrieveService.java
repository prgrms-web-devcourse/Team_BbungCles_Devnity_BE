package com.devnity.devnity.domain.admin.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.admin.entity.Invitation;
import com.devnity.devnity.domain.admin.repository.InvitationRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AdminRetrieveService {

  private final InvitationRepository repository;

  public Invitation getInvitation(String uuid) {
    return repository.findByUuid(uuid)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("초대링크를 찾을 수 없습니다. (uuid : %s)", uuid),
        ErrorCode.LINK_NOT_FOUND
      ));
  }

}
