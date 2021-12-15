package com.devnity.devnity.domain.mapgakco.service.mapgakcocomment;

import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoCommentConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoCommentService {

  private final MapgakcoCommentRepository commentRepository;
  private final MapgakcoCommentConverter commentConverter;
  private final MapgakcoRetrieveService mapgakcoRetrieveService;

  @Transactional
  public void create(Long mapgakcoId, Long userId, MapgakcoCommentCreateRequest request) {
    Mapgakco mapgakco = mapgakcoRetrieveService.getMapgakcoById(mapgakcoId);
    User user = mapgakcoRetrieveService.getUserById(userId);

    MapgakcoComment parentComment = null;
    if (request.getParentId() != null) {
      parentComment = mapgakcoRetrieveService.getCommentById(request.getParentId());
      if (parentComment.getParent() != null) {
        throw new InvalidValueException(
          String.format("The comment for id = %d already has parents. = %d.", parentComment),
          ErrorCode.INVALID_MAPGAKCO_PARENT_COMMENT);
      }
    }
    commentRepository.save(commentConverter.toComment(mapgakco, user, parentComment, request));
  }

  @Transactional
  public void delete(Long commentId) {
    mapgakcoRetrieveService.getCommentById(commentId).delete();
  }
}
