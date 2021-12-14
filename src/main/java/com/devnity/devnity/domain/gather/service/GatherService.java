package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.domain.gather.dto.GatherCommentDto;
import com.devnity.devnity.domain.gather.dto.response.GatherDetailResponse;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.dto.GatherSubCommentDto;
import com.devnity.devnity.domain.gather.dto.request.CreateGatherRequest;
import com.devnity.devnity.domain.gather.dto.response.CreateGatherResponse;
import com.devnity.devnity.domain.gather.dto.response.SuggestGatherResponse;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.ArrayList;
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
  public CreateGatherResponse createGather(Long userId, CreateGatherRequest request) {
    User me = userRetrieveService.getUser(userId);
    Gather saved = gatherRepository.save(Gather.of(me, request));
    return CreateGatherResponse.of(saved.getStatus());
  }

  public SuggestGatherResponse suggestGather() {
    return SuggestGatherResponse.of(
      gatherRepository.findForSuggest(GATHER_SUGGESTION_SIZE).stream()
        .map(SimpleGatherInfoDto::of)
        .collect(Collectors.toList())
    );
  }

  // FIXME : 마지막 페이지 체크
  public CursorPageResponse<SimpleGatherInfoDto> lookUpGatherBoard(
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
      } else {
        lastId = null;
        size -= pageOfGathering.size();
      }
    }
    // CLOSED, FULL 상태 게시물 탐색
    if (!isSizeSatisfied) {
      pageOfOther = gatherRepository.findByPaging(category, List.of(GatherStatus.CLOSED, GatherStatus.FULL), lastId, size);
    }

    List<SimpleGatherInfoDto> values = Stream.concat(pageOfGathering.stream(), pageOfOther.stream())
      .map(SimpleGatherInfoDto::of)
      .collect(Collectors.toList());
    return new CursorPageResponse<>(values, values.get(values.size() - 1).getGatherId());
  }

  // TODO : 조회수 up 및 댓글, 신청자 수
  public GatherDetailResponse lookUpGatherDetail(Long userId, Long gatherId) {
    Gather gather = gatherRetrieveService.getGather(gatherId);

    boolean isApplied = gatherRetrieveService.getIsApplied(userId, gatherId);

    List<SimpleUserInfoDto> participants = gather.getApplicants().stream()
      .map(applicant -> applicant.getUser())
      .map(user -> SimpleUserInfoDto.of(user, user.getIntroduction().getProfileImgUrl()))
      .collect(Collectors.toList());

    List<GatherCommentDto> comments = new ArrayList<>();
    for (GatherComment comment : gather.getComments()) {
      // 부모 댓글만 고른다
      if (comment.getParent() == null) {
        // 부모 댓글에 달린 대댓글 리스트를 생성
        List<GatherSubCommentDto> subComments = gatherRetrieveService.getComments(gather, comment).stream()
          .map(subComment -> GatherSubCommentDto.of(subComment))
          .collect(Collectors.toList());
        comments.add(GatherCommentDto.of(comment, subComments));
      }
    }

    return GatherDetailResponse.of(gather, isApplied, participants, comments);
  }

}
