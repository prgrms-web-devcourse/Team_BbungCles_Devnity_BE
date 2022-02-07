package com.devnity.devnity.domain.introduction.service;

import com.devnity.devnity.common.api.CursorPageRequest;
import com.devnity.devnity.common.api.CursorPageResponse;
import com.devnity.devnity.domain.introduction.dto.IntroductionCommentDto;
import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.dto.response.SuggestResponse;
import com.devnity.devnity.domain.introduction.dto.response.UserDetailIntroductionResponse;
import com.devnity.devnity.domain.introduction.dto.response.UserIntroductionResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.web.user.dto.UserDto;
import com.devnity.devnity.web.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.web.user.service.UserRetrieveService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IntroductionService {

  private static final int SUGGESTION_SIZE = 15;

  private final IntroductionRepository introductionRepository;

  private final IntroductionCommentService introductionCommentService;

  private final IntroductionLikeService introductionLikeService;

  private final UserRetrieveService userRetrieveService;

  @Transactional
  public void save(Long userId, Long introductionId, SaveIntroductionRequest request) {
    Introduction introduction =
        IntroductionServiceUtils.findIntroductionByIdAndUserId(
            introductionRepository, introductionId, userId);

    introduction.update(request.toEntity());
  }

  public List<SuggestResponse> suggest(Long userId) {
    User user = userRetrieveService.getUser(userId);
    return userRetrieveService.getAllByCourseAndGenerationLimit(user, SUGGESTION_SIZE).stream()
        .map(
            u ->
                SuggestResponse.of(
                    UserDto.of(u),
                    IntroductionDto.of(
                        u.getIntroduction(),
                        getLikeCount(u.getIntroduction()),
                        getCommentCount(u.getIntroduction()))))
        .collect(Collectors.toList());
  }

  public CursorPageResponse<UserIntroductionResponse> search(SearchIntroductionRequest searchRequest, CursorPageRequest pageRequest) {

    List<UserIntroductionResponse> values = introductionRepository
      .findAllBy(searchRequest, pageRequest.getLastId(), pageRequest.getSize())
      .stream()
      .map(
        i ->
          UserIntroductionResponse.of(
            UserDto.of(i.getUser()),
            IntroductionDto.of(i, getLikeCount(i), getCommentCount(i))))
      .collect(Collectors.toList());

    Long lastId = getLastId(values, pageRequest.getLastId());
    return new CursorPageResponse<>(values, lastId);
  }

  private Long getLastId(List<UserIntroductionResponse> values, Long lastId) {
    if (values.isEmpty())
      return lastId;

    return values.get(values.size() - 1).getIntroduction().getIntroductionId();
  }

  private long getCommentCount(Introduction introduction) {
    return introductionCommentService.countBy(introduction.getId());
  }

  private long getLikeCount(Introduction introduction) {
    return introductionLikeService.countBy(introduction.getId());
  }

  public UserDetailIntroductionResponse getUserDetailIntroduction(Long me, Long introductionId) {
    Introduction introduction =
      IntroductionServiceUtils.findIntroductionById(
        introductionRepository, introductionId);

    User user = introduction.getUser();

    List<IntroductionCommentDto> comments = introductionCommentService.getCommentsBy(
      introductionId);

    return UserDetailIntroductionResponse.of(
        UserDto.of(user),
        IntroductionDto.of(
            introduction,
            introduction.getDescription(),
            getLikeCount(introduction),
            getCommentCount(introduction)),
        comments,
        isLiked(me, introductionId));
  }

  private boolean isLiked(Long userId, Long introductionId) {
    return introductionLikeService.existsBy(userId, introductionId);
  }
}
