package com.devnity.devnity.domain.mapgakco.converter;

import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.response.MapgakcoCommentResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;
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

  public MapgakcoCommentResponse toMapgakcoCommentResponse(
    MapgakcoComment comment,
    SimpleUserInfoDto writer,
    List<MapgakcoCommentResponse> children
  ) {
    return MapgakcoCommentResponse.builder()
      .commentId(comment.getId())
      .content(comment.getContent())
      .status(comment.getStatus())
      .createdAt(comment.getCreatedAt())
      .updatedAt(comment.getModifiedAt())
      .writer(writer)
      .children(children)
      .build();
  }

}
