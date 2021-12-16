package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.gather.dto.GatherChildCommentDto;
import com.devnity.devnity.domain.gather.dto.GatherCommentDto;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.dto.request.UpdateGatherRequest;
import com.devnity.devnity.domain.gather.dto.response.GatherDetailResponse;
import com.devnity.devnity.domain.gather.dto.response.GatherStatusResponse;
import com.devnity.devnity.domain.gather.dto.response.SuggestGatherResponse;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

  private static final int GATHER_SUGGESTION_SIZE = 5;

  // TODO : 모집 등록시 슬랙 알림
  @Transactional
  public GatherStatusResponse createGather(Long userId, CreateGatherRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather gather = gatherRepository.save(Gather.of(me, request));
    return GatherStatusResponse.of(gather);
  }

  @Transactional
  public GatherStatusResponse updateGather(Long userId, Long gatherId, UpdateGatherRequest request) {
    Gather gather = gatherRetrieveService.getGather(gatherId);

    if (!gather.isWrittenBy(userId)) {
      throw new InvalidValueException(
        String.format("작성자만이 모집 게시글을 수정할 수 있음 (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.GATHER_UPDATE_NOT_ALLOWED
      );
    }
    gather.update(request.getTitle(), request.getContent(), request.getDeadline(), request.getApplicantLimit());
    return GatherStatusResponse.of(gather);
  }

  @Transactional
  public GatherStatusResponse deleteGather(Long userId, Long gatherId) {
    Gather gather = gatherRetrieveService.getGather(gatherId);

    if (!gather.isWrittenBy(userId)) {
      throw new InvalidValueException(
        String.format("작성자만이 모집 게시글을 삭제할 수 있음 (gatherId : %d, userID : %d)", gatherId, userId),
        ErrorCode.GATHER_DELETE_NOT_ALLOWED
      );
    }
    gather.delete();
    return GatherStatusResponse.of(gather);
  }

  public SuggestGatherResponse suggestGather() {
    return SuggestGatherResponse.of(
      gatherRepository.findForSuggest(GATHER_SUGGESTION_SIZE).stream()
        .map(SimpleGatherInfoDto::of)
        .collect(Collectors.toList())
    );
  }

  public CursorPageResponse<SimpleGatherInfoDto> lookUpGatherBoard(GatherCategory category, CursorPageRequest pageRequest) {
    // Initialize
    Long lastId = pageRequest.getLastId();
    Integer size = pageRequest.getSize();
    List<Gather> gathers = new ArrayList<>();

    // GATHERING 상태의 게시물 탐색
    if (lastId == null || gatherRetrieveService.getGather(lastId).isGathering()) {
      gathers.addAll(
        gatherRepository.findByPaging(category, GatherStatus.available(), lastId, size)
      );
      if (gathers.size() == size) {
        return createPageResponse(gathers);
      }
      lastId = null;
      size -= gathers.size();
    }
    // CLOSED, FULL 상태 게시물 탐색
    gathers.addAll(
      gatherRepository.findByPaging(category, GatherStatus.unavailable(), lastId, size)
    );

    return createPageResponse(gathers);
  }

  @Transactional
  public GatherDetailResponse lookUpGatherDetail(Long userId, Long gatherId) {
    Gather gather = gatherRetrieveService.getGather(gatherId);
    gather.increaseView();

    boolean isApplied = gatherRetrieveService.getIsApplied(userId, gatherId);

    List<GatherCommentDto> comments = new ArrayList<>();
    for (GatherComment comment : gather.getComments()) {
      // 부모 댓글만 고른다
      if (comment.getParent() == null) {
        // 부모 댓글에 달린 대댓글 리스트를 생성
        // (* 양방향 매핑의 댓글 리스트 사용시 N+1 문제가 발생하므로 그냥 쿼리를 날림)
        List<GatherChildCommentDto> children = gatherRetrieveService.getComments(gather, comment).stream()
          .map(childComment -> GatherChildCommentDto.of(childComment))
          .collect(Collectors.toList());
        comments.add(GatherCommentDto.of(comment, children));
      }
    }

    return GatherDetailResponse.of(gather, isApplied, comments);
  }

// -------------------------------------- ( utils ) --------------------------------------

  private CursorPageResponse<SimpleGatherInfoDto> createPageResponse(List<Gather> gathers) {
    List<SimpleGatherInfoDto> values = gathers.stream()
      .map(gather -> SimpleGatherInfoDto.of(gather))
      .collect(Collectors.toList());
    Long nextLastId = values.size() == 0 ? null : values.get(values.size() - 1).getGatherId();
    return new CursorPageResponse<>(values, nextLastId);
  }

}
