package com.devnity.devnity.domain.introduction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.introduction.dto.IntroductionCommentDto;
import com.devnity.devnity.domain.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.request.UpdateIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.entity.IntroductionCommentStatus;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IntroductionCommentServiceTest {

  @InjectMocks private IntroductionCommentService introductionCommentService;

  @Mock private UserRetrieveService userRetrieveService;

  @Mock private IntroductionCommentRepository introductionCommentRepository;

  @Mock private UserRepository userRepository;

  @Mock private IntroductionRepository introductionRepository;
  
  @DisplayName("자기소개 댓글을 저장한다")
  @Test 
  public void testSave() throws Exception {
    // given
    SaveIntroductionCommentRequest request = SaveIntroductionCommentRequest.builder()
      .content("content").build();

    User user = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();

    Introduction introduction = user.getIntroduction();

    IntroductionComment comment = request.toEntity(user, introduction, null);

    given(userRetrieveService.getUser(anyLong())).willReturn(user);
    given(introductionRepository.findById(anyLong()))
        .willReturn(Optional.of(introduction));
    given(introductionCommentRepository.save(any())).willReturn(comment);

    // when
    SaveIntroductionCommentResponse response = introductionCommentService.save(1L, 1L, request);

    // then
    verify(userRetrieveService).getUser(anyLong());
    verify(introductionRepository).findById(anyLong());
    verify(introductionCommentRepository).save(any());
    assertThat(response.getParentId()).isNull();
  }

  @DisplayName("자기소개 대댓글을 저장한다")
  @Test
  public void testSaveSubComment() throws Exception {
    // given
    SaveIntroductionCommentRequest request = SaveIntroductionCommentRequest.builder()
      .content("content").parentId(1L).build();

    User user = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();

    Introduction introduction = user.getIntroduction();

    IntroductionComment parent = mock(IntroductionComment.class);

    IntroductionComment comment = request.toEntity(user, introduction, parent);

    given(userRetrieveService.getUser(anyLong())).willReturn(user);
    given(introductionRepository.findById(anyLong()))
        .willReturn(Optional.of(introduction));
    given(introductionCommentRepository.findById(anyLong())).willReturn(Optional.of(parent));
    given(parent.getId()).willReturn(1L);
    given(introductionCommentRepository.save(any())).willReturn(comment);

    // when
    SaveIntroductionCommentResponse response = introductionCommentService.save(1L, 1L, request);

    // then
    verify(userRetrieveService).getUser(anyLong());
    verify(introductionRepository).findById(anyLong());
    verify(introductionCommentRepository).findById(any());
    verify(introductionCommentRepository).save(any());
    assertThat(response.getParentId()).isEqualTo(1L);
  }

  @DisplayName("자기소개 댓글을 수정한다")
  @Test 
  public void testUpdateContent() throws Exception {
    // given
    UpdateIntroductionCommentRequest request = new UpdateIntroductionCommentRequest(
      "update content");

    User user = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();

    Introduction introduction = user.getIntroduction();

    IntroductionComment comment = IntroductionComment.of("content", user, user.getIntroduction());

    given(
      introductionCommentRepository.findByIdAndUserIdAndIntroductionId(
        anyLong(), anyLong(), anyLong()))
      .willReturn(Optional.of(comment));

    // when
    introductionCommentService.update(1L, 2L, 3L, request);

    // then
    assertThat(comment.getContent()).isEqualTo(request.getContent());
  }

  @DisplayName("자기소개가 없으면 수정할 수 없다")
  @Test
  public void testUpdateContentNotExist() throws Exception {
    // given
    UpdateIntroductionCommentRequest request = new UpdateIntroductionCommentRequest(
      "update content");

    User user = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();

    Introduction introduction = user.getIntroduction();


    given(
      introductionCommentRepository.findByIdAndUserIdAndIntroductionId(
        anyLong(), anyLong(), anyLong()))
      .willReturn(Optional.empty());

    // when // then
    assertThatThrownBy(() -> introductionCommentService.update(1L, 2L, 3L, request))
        .isInstanceOf(EntityNotFoundException.class);
  }
  
  @DisplayName("댓글을 삭제한다")
  @Test 
  public void testDeleteComment() throws Exception { 
    //given
    User user = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();

    IntroductionComment comment = IntroductionComment.of("content", user, user.getIntroduction());

    given(
            introductionCommentRepository.findByIdAndUserIdAndIntroductionId(
                anyLong(), anyLong(), anyLong()))
        .willReturn(Optional.of(comment));
    // when
    introductionCommentService.delete(1L, 1L, 1L);

    // then
    verify(introductionCommentRepository)
        .findByIdAndUserIdAndIntroductionId(anyLong(), anyLong(), anyLong());
    assertThat(comment.getStatus()).isEqualTo(IntroductionCommentStatus.DELETED);
  } 

  @DisplayName("이미 삭제된 댓글은 다시 삭제될 수 없다")
  @Test
  public void testDeleteCommentDouble() throws Exception {
    //given
    User user = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();

    IntroductionComment comment = IntroductionComment.of("content", user, user.getIntroduction());
    comment.delete();

    given(
            introductionCommentRepository.findByIdAndUserIdAndIntroductionId(
                anyLong(), anyLong(), anyLong()))
        .willReturn(Optional.of(comment));

    // when    // then
    assertThatThrownBy(() -> introductionCommentService.delete(1L, 1L, 1L))
        .isInstanceOf(InvalidValueException.class);
  }

  @DisplayName("자기소개 댓글을 가져올 수 있다")
  @Test 
  public void testGetCommentsBy() throws Exception {
    //given
    User user1 = User.builder()
      .role(UserRole.STUDENT)
      .course(new Course("FEz"))
      .generation(new Generation(1))
      .name("함승훈")
      .password("Password123!")
      .email("email@gmail.com")
      .build();


    List<IntroductionComment> parents = new ArrayList<>();
    parents.add(IntroductionComment.of("parent1", user1, user1.getIntroduction()));
    parents.add(IntroductionComment.of("parent2", user1, user1.getIntroduction()));

    List<IntroductionComment> children = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      children.add(IntroductionComment.of("child" + i, user1, user1.getIntroduction(), parents.get(0)));
    }

    given(introductionCommentRepository.findAllParentsByDesc(any())).willReturn(parents);
    given(introductionCommentRepository.findAllChildrenByDesc(parents.get(0))).willReturn(children);
    given(userRetrieveService.getSimpleUserInfo(any()))
        .willReturn(SimpleUserInfoDto.of(user1, null));

    // when
    List<IntroductionCommentDto> comments = introductionCommentService.getCommentsBy(1L);

    // then
    assertThat(comments).hasSize(parents.size());
    assertThat(comments.get(0).getChildren()).hasSize(children.size());
    assertThat(comments.get(1).getChildren()).isEmpty();
  }
  
  
  
}