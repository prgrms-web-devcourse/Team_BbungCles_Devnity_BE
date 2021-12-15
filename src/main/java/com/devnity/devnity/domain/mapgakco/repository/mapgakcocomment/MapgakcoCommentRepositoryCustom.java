package com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import java.util.List;

public interface MapgakcoCommentRepositoryCustom {

  List<MapgakcoComment> getParentByMapgakcoWithUser(Mapgakco mapgakco);

  List<MapgakcoComment> getChildByParentWithUser(MapgakcoComment parent);

}
