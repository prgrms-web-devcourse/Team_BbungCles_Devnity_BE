package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatherCommentRepository extends JpaRepository<GatherComment, Long> {

  List<GatherComment> findByGatherAndParent(Gather gather, GatherComment parent);
}
