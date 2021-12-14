package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.domain.gather.dto.GatherDto;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherService {

  private final UserRetrieveService userRetrieveService;
  private final GatherRetrieveService gatherRetrieveService;

  private final GatherRepository gatherRepository;


  @Transactional
  public GatherStatus createGather(Long userId, CreateGatherRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather saved = gatherRepository.save(Gather.of(me, request));
    return saved.getStatus();
  }

  public CursorPageResponse<GatherCardResponse> getGatherCards(
    GatherCategory category,
    CursorPageRequest pageRequest
  ) {
    Long lastId = pageRequest.getLastId();
    Integer size = pageRequest.getSize();
    List<Gather> pageOfGathering = Collections.emptyList();
    List<Gather> pageOfOther = Collections.emptyList();
    boolean isSizeSatisfied = false;

    if (gatherRetrieveService.getGather(lastId).getStatus() == GatherStatus.GATHERING) {
      pageOfGathering = gatherRepository.findByPaging(category, List.of(GatherStatus.GATHERING), lastId, size);
      if (pageOfGathering.size() == size) {
        isSizeSatisfied = true;
      }
    }
    if (!isSizeSatisfied) {
      pageOfOther = gatherRepository.findByPaging(category, List.of(GatherStatus.CLOSED, GatherStatus.FULL), lastId, size);
    }

    List<GatherCardResponse> values = Stream.concat(pageOfGathering.stream(), pageOfOther.stream())
      .map(GatherCardResponse::of)
      .collect(Collectors.toList());

    return new CursorPageResponse<>(values, values.get(values.size() - 1).getGatherId());
  }

}
