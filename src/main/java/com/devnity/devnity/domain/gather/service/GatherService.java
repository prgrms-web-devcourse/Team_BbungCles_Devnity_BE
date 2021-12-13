package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.dto.response.GatherCardResponse;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherService {

  private final UserRetrieveService userRetrieveService;
  private final GatherRepository gatherRepository;

  @Transactional
  public GatherStatus createGather(Long userId, CreateGatherRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather saved = gatherRepository.save(Gather.of(me, request));
    return saved.getStatus();
  }

//  public CursorPageResponse<GatherCardResponse> getGatherCards(
//    GatherCategory category,
//    CursorPageRequest pageRequest
//  ) {
//
//  }

}
