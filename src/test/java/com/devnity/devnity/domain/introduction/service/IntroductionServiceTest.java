package com.devnity.devnity.domain.introduction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.devnity.devnity.web.common.dto.CursorPageRequest;
import com.devnity.devnity.web.common.dto.CursorPageResponse;
import com.devnity.devnity.domain.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.dto.response.SuggestResponse;
import com.devnity.devnity.domain.introduction.dto.response.UserIntroductionResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.web.introduction.service.IntroductionCommentService;
import com.devnity.devnity.web.introduction.service.IntroductionLikeService;
import com.devnity.devnity.web.introduction.service.IntroductionService;
import com.devnity.devnity.web.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IntroductionServiceTest {

  @InjectMocks private IntroductionService introductionService;

  @Mock private IntroductionLikeService introductionLikeService;

  @Mock private IntroductionCommentService introductionCommentService;

  @Mock private UserRetrieveService userRetrieveService;

  @Mock private IntroductionRepository introductionRepository;

  @Mock private UserRepository userRepository;

  @DisplayName("자기소개가 저장된다")
  @Test
  public void testSaveIntroduction() throws Exception {
    // given
    SaveIntroductionRequest request = SaveIntroductionRequest.builder()
        .blogUrl("blog")
        .description("description")
        .githubUrl("github")
        .latitude(123.123)
        .longitude(445.455)
        .mbti(Mbti.ENFJ)
        .profileImgUrl("profile")
        .summary("summary")
        .build();

    User user = User.builder()
        .email("email@gmail.com")
        .role(UserRole.STUDENT)
        .password("password")
        .name("seunghun")
        .generation(new Generation(1))
        .course(new Course("FE"))
        .build();

    Introduction introduction = user.getIntroduction();

    given(introductionRepository.findIntroductionByIdAndUserId(1L, 1L)).willReturn(Optional.of(introduction));
    // when
    introductionService.save(1L, 1L, request);

    // then
    assertThat(introduction.getDescription()).isEqualTo(request.getDescription());
    assertThat(introduction.getBlogUrl()).isEqualTo(request.getBlogUrl());
    assertThat(introduction.getGithubUrl()).isEqualTo(request.getGithubUrl());
    assertThat(introduction.getLatitude()).isEqualTo(request.getLatitude());
    assertThat(introduction.getLongitude()).isEqualTo(request.getLongitude());
    assertThat(introduction.getMbti()).isEqualTo(request.getMbti());
    assertThat(introduction.getSummary()).isEqualTo(request.getSummary());
    assertThat(introduction.getProfileImgUrl()).isEqualTo(request.getProfileImgUrl());
  }
  
  @DisplayName("자기소개 추천: 사용자와 기수, 코스가 동일하다")
  @Test 
  public void testSuggest() throws Exception {
    // given

    List<User> users = new ArrayList<>();
    int size = 5;

    for (int i = 0; i < size; i++) {
      User user = User.builder()
        .course(new Course("FE"))
        .generation(new Generation(1))
        .password("Password")
        .role(UserRole.STUDENT)
        .name("name" + i)
        .email(i + "email@naver.com")
        .build();

      Introduction introduction = user.getIntroduction();
      introduction.update(Introduction.builder()
        .description("content" + i)
        .summary("summary")
        .mbti(Mbti.ENFJ)
        .profileImgUrl("profile" + i)
        .longitude(123.123)
        .latitude(123.123)
        .blogUrl("blog")
        .githubUrl("github")
        .build());

      users.add(user);
    }

    User user = users.remove(0);
    given(userRetrieveService.getUser(any())).willReturn(user);
    given(userRetrieveService.getAllByCourseAndGenerationLimit(any(), anyInt())).willReturn(users);
    given(introductionCommentService.countBy(any())).willReturn(0L);
    given(introductionLikeService.countBy(any())).willReturn(0L);

    // when
    List<SuggestResponse> suggest = introductionService.suggest(user.getId());

    // then
    assertThat(suggest).hasSize(size - 1);
    assertThat(suggest.get(0).getUser().getGeneration()).isEqualTo(user.getGenerationSequence());
    assertThat(suggest.get(0).getUser().getCourse()).isEqualTo(user.getCourseName());
  }  
  
  @DisplayName("자기소개 필터링 + 페이징 조회")
  @Test 
  public void testSearch() throws Exception {
    // given
    List<User> users = new ArrayList<>();
    int size = 5;

    for (int i = 0; i < size; i++) {
      User user = User.builder()
        .course(new Course("FE"))
        .generation(new Generation(1))
        .password("Password")
        .role(UserRole.STUDENT)
        .name("name" + i)
        .email(i + "email@naver.com")
        .build();

      Introduction introduction = user.getIntroduction();
      introduction.update(Introduction.builder()
        .description("content" + i)
        .summary("summary")
        .mbti(Mbti.ENFJ)
        .profileImgUrl("profile" + i)
        .longitude(123.123)
        .latitude(123.123)
        .blogUrl("blog")
        .githubUrl("github")
        .build());

      users.add(user);
    }

    User user = users.get(0);
    List<Introduction> introductions = users.stream().map(User::getIntroduction)
      .collect(Collectors.toList());

    given(introductionRepository.findAllBy(any(), anyLong(), anyInt())).willReturn(introductions);
    given(introductionCommentService.countBy(any())).willReturn(0L);
    given(introductionLikeService.countBy(any())).willReturn(0L);

    // when
    SearchIntroductionRequest request = SearchIntroductionRequest.builder()
      .role(user.getRole())
      .name(user.getName())
      .generation(user.getGenerationSequence())
      .course(user.getCourseName())
      .build();

    CursorPageResponse<UserIntroductionResponse> result = introductionService.search(request,
      new CursorPageRequest(5L, 5));

    // then
    assertThat(result.getValues()).hasSize(size);
    assertThat(result.getValues())
        .filteredOnAssertions((value) -> assertThat(value.getUser().getCourse()).isEqualTo("FE"))
        .hasSize(size);
  }
  
  
}