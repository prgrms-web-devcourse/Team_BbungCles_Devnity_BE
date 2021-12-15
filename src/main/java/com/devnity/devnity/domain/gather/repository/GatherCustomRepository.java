package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;

public interface GatherCustomRepository {

  List<Gather> findByPaging(GatherCategory category, List<GatherStatus> statuses, Long lastId, int size);

  List<Gather> findForSuggest(int size);

  List<Gather> findGathersHostedBy(User host);

  List<Gather> findGathersAppliedBy(User applicant);
}
