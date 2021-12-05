package com.devnity.devnity.domain.gather.repository;

import com.devnity.devnity.domain.gather.entity.GatherComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GatherCommentRepository extends JpaRepository<GatherComment, Long> {
}
