package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import com.devnity.devnity.domain.gather.entity.category.GatherStatus;
import java.util.List;

public interface GatherCustomRepository {

  List<Gather> findByPaging(GatherCategory category, List<GatherStatus> statuses, Long lastId, int size);

  List<Gather> findForSuggest(int size);

}
