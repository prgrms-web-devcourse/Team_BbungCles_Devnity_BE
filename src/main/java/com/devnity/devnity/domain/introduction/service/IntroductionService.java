package com.devnity.devnity.domain.introduction.service;

import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IntroductionService {

  private final IntroductionRepository introductionRepository;

  @Transactional
  public void save(Long userId, Long introductionId, SaveIntroductionRequest request) {
    Introduction introduction = introductionRepository
        .findByIdAndUserId(introductionId, userId)
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    String.format(
                        "There is no introduction. userId=%d, introductionId=%d",
                        userId, introductionId)));

    introduction.update(request.toEntity());
  }
}
