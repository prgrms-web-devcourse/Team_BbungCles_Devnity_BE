package com.devnity.devnity.domain.mapgakco.service.mapgakcocomment;

import com.devnity.devnity.common.error.exception.BusinessException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.common.error.exception.InvalidValueException;
import com.devnity.devnity.domain.mapgakco.converter.MapgakcoCommentConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentUpdateRequest;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.response.MapgakcoCommentResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoRetrieveService;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
      parentComment = mapgakcoRetrieveService.getPostedCommentById(request.getParentId());
      if (parentComment.getParent() != null) {
        throw new InvalidValueException(ErrorCode.INVALID_MAPGAKCO_PARENT_COMMENT);
      }
    }
    commentRepository.save(commentConverter.toComment(mapgakco, user, parentComment, request));
  }

  public List<MapgakcoCommentResponse> getAllCommentByMapgakco(Mapgakco mapgakco) {
    List<MapgakcoComment> parents = mapgakcoRetrieveService.getAllParentCommentByMapgakcoWithUser(mapgakco);

    List<MapgakcoCommentResponse> list = new ArrayList<>();
    for (MapgakcoComment parent : parents) {
      List<MapgakcoCommentResponse> children = mapgakcoRetrieveService.getAllChildCommentByParentWithUser(parent).stream()
        .map(comment -> commentConverter.toMapgakcoCommentResponse(comment, SimpleUserInfoDto.of(comment.getUser()), null))
        .collect(Collectors.toList());

      MapgakcoCommentResponse parentResponse = commentConverter.toMapgakcoCommentResponse(parent,
        SimpleUserInfoDto.of(parent.getUser()), children);
      list.add(parentResponse);
    }
    return list;
  }

  @Transactional
  public void update(Long userId, Long commentId, MapgakcoCommentUpdateRequest request) {
    MapgakcoComment foundComment = mapgakcoRetrieveService.getPostedCommentById(commentId);
    if (userId.equals(foundComment.getUser().getId())) {
      foundComment.update(request.getContent());
    } else {
      throw new BusinessException(ErrorCode.UPDATE_MAPGAKCO_NOT_ALLOWED);
    }
  }

  @Transactional
  public void delete(Long userId, Long commentId) {
    MapgakcoComment foundComment = mapgakcoRetrieveService.getPostedCommentById(commentId);
    if (userId.equals(foundComment.getUser().getId())) {
      foundComment.delete();
    } else {
      throw new BusinessException(ErrorCode.DELETE_MAPGAKCO_NOT_ALLOWED);
    }
  }

}

