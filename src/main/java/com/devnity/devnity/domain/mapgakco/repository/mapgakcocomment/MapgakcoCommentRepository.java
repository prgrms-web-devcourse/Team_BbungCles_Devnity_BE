package com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoCommentStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapgakcoCommentRepository extends JpaRepository<MapgakcoComment, Long>,
  MapgakcoCommentRepositoryCustom {

  Optional<MapgakcoComment> findByIdAndStatusNot(Long id, MapgakcoCommentStatus status);

  List<MapgakcoComment> getByMapgakcoAndParentIsNull(Mapgakco mapgakco);

  List<MapgakcoComment> getByParent(MapgakcoComment parent);

}
