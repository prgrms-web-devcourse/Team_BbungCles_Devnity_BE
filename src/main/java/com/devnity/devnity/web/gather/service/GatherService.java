package com.devnity.devnity.web.gather.service;

import com.devnity.devnity.domain.gather.service.GatherRetrieveService;
import com.devnity.devnity.web.common.dto.CursorPageRequest;
import com.devnity.devnity.web.common.dto.CursorPageResponse;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.web.gather.dto.GatherCommentDto;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.web.gather.dto.GatherChildCommentDto;
import com.devnity.devnity.web.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.web.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.web.gather.dto.request.UpdateGatherRequest;
import com.devnity.devnity.web.gather.dto.response.GatherDetailResponse;
import com.devnity.devnity.web.gather.dto.response.GatherStatusResponse;
import com.devnity.devnity.web.gather.dto.response.SuggestGatherResponse;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.event.CreateGatherEvent;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherService {

  private final ApplicationEventPublisher publisher;

  private final UserRetrieveService userRetrieveService;
  private final GatherRetrieveService gatherRetrieveService;

  private final GatherRepository gatherRepository;

  private static final int GATHER_SUGGESTION_SIZE = 5;

  @Transactional
  public GatherStatusResponse createGather(Long userId, CreateGatherRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather gather = gatherRepository.save(Gather.of(me, request));

    publisher.publishEvent(new CreateGatherEvent(SimpleUserInfoDto.of(me), SimpleGatherInfoDto.of(gather)));
    return GatherStatusResponse.of(gather);
  }

  @Transactional
  public GatherStatusResponse updateGather(Long userId, Long gatherId, UpdateGatherRequest request) {
    Gather gather = gatherRetrieveService.getGather(gatherId);

    if (!gather.isWrittenBy(userId)) {
      throw new InvalidValueException(
        String.format("??????????????? ?????? ???????????? ????????? ??? ?????? (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.UPDATE_GATHER_NOT_ALLOWED
      );
    }
    gather.update(request.getTitle(), request.getContent(), request.getCategory(), request.getDeadline() );
    return GatherStatusResponse.of(gather);
  }

  @Transactional
  public GatherStatusResponse deleteGather(Long userId, Long gatherId) {
    Gather gather = gatherRetrieveService.getGather(gatherId);

    if (!gather.isWrittenBy(userId)) {
      throw new InvalidValueException(
        String.format("??????????????? ?????? ???????????? ????????? ??? ?????? (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.DELETE_GATHER_NOT_ALLOWED
      );
    }
    gather.delete();
    return GatherStatusResponse.of(gather);
  }

  @Transactional
  public GatherStatusResponse closeGather(Long userId, Long gatherId){
    Gather gather = gatherRetrieveService.getGather(gatherId);

    if (!gather.isWrittenBy(userId)) {
      throw new InvalidValueException(
        String.format("??????????????? ????????? ????????? ??? ?????? (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.CLOSE_GATHER_NOT_ALLOWED
      );
    }
    gather.close();
    return GatherStatusResponse.of(gather);
  }

  public SuggestGatherResponse suggestGather() {
    return SuggestGatherResponse.of(
      gatherRepository.findGathersForSuggest(GATHER_SUGGESTION_SIZE).stream()
        .map(SimpleGatherInfoDto::of)
        .collect(Collectors.toList())
    );
  }

  public CursorPageResponse<SimpleGatherInfoDto> lookUpGatherBoard(String title, GatherCategory category, CursorPageRequest pageRequest) {
    // Initialize
    Long lastId = pageRequest.getLastId();
    Integer size = pageRequest.getSize();
    List<Gather> gathers = new ArrayList<>();

    // GATHERING ????????? ????????? ??????
    if (lastId == null || gatherRetrieveService.getGather(lastId).isGathering()) {
      gathers.addAll(
        gatherRepository.findGathersByPaging(title, category, GatherStatus.available(), lastId, size)
      );
      if (gathers.size() == size) {
        return SimpleGatherInfoDto.createPage(gathers);
      }
      lastId = null;
      size -= gathers.size();
    }
    // CLOSED, FULL ?????? ????????? ??????
    gathers.addAll(
      gatherRepository.findGathersByPaging(title, category, GatherStatus.unavailable(), lastId, size)
    );

    return SimpleGatherInfoDto.createPage(gathers);
  }

  @Transactional
  public GatherDetailResponse lookUpGatherDetail(Long userId, Long gatherId) {
    Gather gather = gatherRetrieveService.getGather(gatherId);
    gather.increaseView();

    boolean isApplied = gatherRetrieveService.getIsApplied(userId, gatherId);

    List<GatherCommentDto> comments = new ArrayList<>();
    for (GatherComment comment : gather.getComments()) {
      // ?????? ????????? ?????????
      if (comment.getParent() == null) {
        // ?????? ????????? ?????? ????????? ???????????? ??????
        // (* ????????? ????????? ?????? ????????? ????????? N+1 ????????? ??????????????? ?????? ????????? ??????)
        List<GatherChildCommentDto> children = gatherRetrieveService.getComments(gather, comment).stream()
          .map(childComment -> GatherChildCommentDto.of(childComment))
          .collect(Collectors.toList());
        comments.add(GatherCommentDto.of(comment, children));
      }
    }

    return GatherDetailResponse.of(gather, isApplied, comments);
  }

}
