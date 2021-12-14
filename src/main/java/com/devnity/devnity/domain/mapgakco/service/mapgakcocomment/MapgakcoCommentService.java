package com.devnity.devnity.domain.mapgakco.service.mapgakcocomment;

import com.devnity.devnity.domain.mapgakco.converter.MapgakcoCommentConverter;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.request.MapgakcoCommentCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.service.MapgakcoServiceUtils;
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
  private final MapgakcoServiceUtils mapgakcoServiceUtils;

  @Transactional
  public void create(Long mapgakcoId, Long userId, MapgakcoCommentCreateRequest request) {
    Mapgakco mapgakco = mapgakcoServiceUtils.findMapgakcoById(mapgakcoId);
    User user = mapgakcoServiceUtils.findUserById(userId);
    MapgakcoComment parent = commentRepository.findById(request.getParentId()).orElseGet(null);
    commentRepository.save(commentConverter.toComment(mapgakco, user, parent, request));
  }


}
