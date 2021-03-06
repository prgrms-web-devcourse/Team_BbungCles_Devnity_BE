package com.devnity.devnity.domain.introduction.respository.custom;

import com.devnity.devnity.web.introduction.dto.request.SearchIntroductionRequest;
import com.devnity.devnity.domain.introduction.entity.Introduction;
import java.util.List;
import java.util.Optional;

public interface IntroductionCustomRepository {

  Optional<Introduction> findIntroductionByIdAndUserId(Long introductionId, Long userId);

  List<Introduction> findAllBy(SearchIntroductionRequest searchRequest, Long lastId, Integer size);
}
