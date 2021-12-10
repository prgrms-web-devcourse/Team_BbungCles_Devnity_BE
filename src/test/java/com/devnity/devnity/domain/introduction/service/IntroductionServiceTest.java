package com.devnity.devnity.domain.introduction.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.Authority;
import com.devnity.devnity.domain.user.entity.Course;
import com.devnity.devnity.domain.user.entity.Generation;
import com.devnity.devnity.domain.user.entity.Mbti;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.entity.UserRole;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IntroductionServiceTest {

  @InjectMocks private IntroductionService introductionService;

  @Mock private IntroductionRepository introductionRepository;

  @DisplayName("자기소개가 저장된다")
  @Test
  public void testSaveIntroduction() throws Exception {
    // given
    SaveIntroductionRequest request = SaveIntroductionRequest.builder()
        .blogUrl("blog")
        .content("content")
        .githubUrl("github")
        .latitude(123.123)
        .longitude(445.455)
        .mbti(Mbti.ENFA)
        .profileImgUrl("profile")
        .summary("summary")
        .build();

    User user = User.builder()
        .email("email@gmail.com")
        .role(UserRole.STUDENT)
        .password("password")
        .name("seunghun")
        .authority(Authority.USER)
        .generation(new Generation(1))
        .course(new Course("FE"))
        .build();

    Introduction introduction = user.getIntroduction();

    given(introductionRepository.findByIdAndUserId(1L, 1L)).willReturn(Optional.of(introduction));
    // when
    introductionService.save(1L, 1L, request);

    // then
    assertThat(introduction.getContent()).isEqualTo(request.getContent());
    assertThat(introduction.getBlogUrl()).isEqualTo(request.getBlogUrl());
    assertThat(introduction.getGithubUrl()).isEqualTo(request.getGithubUrl());
    assertThat(introduction.getLatitude()).isEqualTo(request.getLatitude());
    assertThat(introduction.getLongitude()).isEqualTo(request.getLongitude());
    assertThat(introduction.getMbti()).isEqualTo(request.getMbti());
    assertThat(introduction.getSummary()).isEqualTo(request.getSummary());
    assertThat(introduction.getProfileImgUrl()).isEqualTo(request.getProfileImgUrl());
  }
  
  
}