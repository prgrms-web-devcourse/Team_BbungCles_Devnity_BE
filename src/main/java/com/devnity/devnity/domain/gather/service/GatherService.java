package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.dto.GatherSimpleInfoDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

  public static final int GATHER_SUGGESTION_SIZE = 5;

  @Transactional
  public GatherStatus createGather(Long userId, CreateGatherRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather saved = gatherRepository.save(Gather.of(me, request));
    return saved.getStatus();
  }

  public List<GatherSimpleInfoDto> gatherSuggest(){
    return gatherRepository.findForSuggest(GATHER_SUGGESTION_SIZE).stream()
      .map(GatherSimpleInfoDto::of)
      .collect(Collectors.toList());
  }

  // FIXME : 마지막 페이지 체크
  public CursorPageResponse<GatherSimpleInfoDto> gatherBoard(
    GatherCategory category,
    CursorPageRequest pageRequest
  ) {
    // Initialize
    Long lastId = pageRequest.getLastId();
    Integer size = pageRequest.getSize();
    List<Gather> pageOfGathering = Collections.emptyList();
    List<Gather> pageOfOther = Collections.emptyList();
    boolean isSizeSatisfied = false;

    // GATHERING 상태의 게시물 탐색
    if (lastId == null || gatherRetrieveService.getGather(lastId).getStatus() == GatherStatus.GATHERING) {
      pageOfGathering = gatherRepository.findByPaging(category, List.of(GatherStatus.GATHERING), lastId, size);
      if (pageOfGathering.size() == size) {
        isSizeSatisfied = true;
      }
      else{
        lastId = null;
        size -= pageOfGathering.size();
      }
    }
    // CLOSED, FULL 상태 게시물 탐색
    if (!isSizeSatisfied) {
      pageOfOther = gatherRepository.findByPaging(category, List.of(GatherStatus.CLOSED, GatherStatus.FULL), lastId, size);
    }

    List<GatherSimpleInfoDto> values = Stream.concat(pageOfGathering.stream(), pageOfOther.stream())
      .map(GatherSimpleInfoDto::of)
      .collect(Collectors.toList());
    return new CursorPageResponse<>(values, values.get(values.size() - 1).getGatherId());
  }

}
