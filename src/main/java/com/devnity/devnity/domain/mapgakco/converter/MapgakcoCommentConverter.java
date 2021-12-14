package com.devnity.devnity.domain.mapgakco.converter;

import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MapgakcoCommentConverter {

  public MapgakcoComment toComment(
    Mapgakco mapgakco,
    User user,
    MapgakcoComment parent,
    MapgakcoCommentCreateRequest request
  ) {
    return MapgakcoComment.builder()
      .content(request.getContent())
      .user(user)
      .mapgakco(mapgakco)
      .parent(parent)
      .build();
  }


}
