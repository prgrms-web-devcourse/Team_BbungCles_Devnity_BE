package com.devnity.devnity.domain.introduction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.devnity.devnity.domain.introduction.dto.request.SaveIntroductionCommentRequest;
import com.devnity.devnity.domain.introduction.dto.response.SaveIntroductionCommentResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.entity.IntroductionComment;
import com.devnity.devnity.domain.introduction.respository.IntroductionCommentRepository;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import com.devnity.devnity.domain.user.repository.UserRepository;
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

    given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
    given(introductionRepository.findIntroductionByIdAndUserId(anyLong(), anyLong()))
        .willReturn(Optional.of(introduction));
    given(introductionCommentRepository.save(any())).willReturn(comment);

    // when
    SaveIntroductionCommentResponse response = introductionCommentService.save(1L, 1L, request);

    // then
    verify(userRepository).findById(anyLong());
    verify(introductionRepository).findIntroductionByIdAndUserId(anyLong(), anyLong());
    verify(introductionCommentRepository).findById(any());
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

    given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
    given(introductionRepository.findIntroductionByIdAndUserId(anyLong(), anyLong()))
        .willReturn(Optional.of(introduction));
    given(introductionCommentRepository.findById(anyLong())).willReturn(Optional.of(parent));
    given(parent.getId()).willReturn(1L);
    given(introductionCommentRepository.save(any())).willReturn(comment);

    // when
    SaveIntroductionCommentResponse response = introductionCommentService.save(1L, 1L, request);

    // then
    verify(userRepository).findById(anyLong());
    verify(introductionRepository).findIntroductionByIdAndUserId(anyLong(), anyLong());
    verify(introductionCommentRepository).findById(any());
    assertThat(response.getParentId()).isEqualTo(1L);
  }
  
  
}