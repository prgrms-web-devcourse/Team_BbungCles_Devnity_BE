package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.category.GatherCategory;
import java.util.List;

public interface GatherCustomRepository {

  List<Gather> findByPaging(GatherCategory category, Long lastId, int size);
}
