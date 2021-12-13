package com.devnity.devnity.domain.introduction.service;

import com.devnity.devnity.domain.introduction.dto.IntroductionDto;
import com.devnity.devnity.domain.introduction.dto.response.SuggestResponse;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import com.devnity.devnity.domain.introduction.respository.IntroductionRepository;
import com.devnity.devnity.domain.user.dto.UserDto;
import com.devnity.devnity.domain.user.dto.request.SaveIntroductionRequest;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.repository.UserRepository;
import com.devnity.devnity.domain.user.service.UserServiceUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IntroductionService {

  private static final int SUGGESTION_SIZE = 15;

  private final IntroductionRepository introductionRepository;

  private final UserRepository userRepository;

  @Transactional
  public void save(Long userId, Long introductionId, SaveIntroductionRequest request) {
    Introduction introduction =
        IntroductionServiceUtils.findIntroductionByIdAndUserId(
            introductionRepository, userId, introductionId);

    introduction.update(request.toEntity());
  }

  public List<SuggestResponse> suggest(Long userId) {
    User user = UserServiceUtils.findUser(userRepository, userId);

    return userRepository
        .findAllByCourseAndGenerationLimit(user, SUGGESTION_SIZE)
        .stream()
        .map(u -> SuggestResponse.of(UserDto.of(u), IntroductionDto.of(u.getIntroduction())))
        .collect(Collectors.toList());
  }

}
