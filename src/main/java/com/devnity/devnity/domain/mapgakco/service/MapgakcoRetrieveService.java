package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoCommentStatus;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoStatus;
import com.devnity.devnity.domain.mapgakco.repository.mapgakco.MapgakcoRepository;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.mapgakcocomment.MapgakcoCommentRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoRetrieveService {

  private final MapgakcoRepository mapgakcoRepository;
  private final MapgakcoApplicantRepository applicantRepository;
  private final MapgakcoCommentRepository commentRepository;
  private final UserRetrieveService userRetrieveService;

  public Mapgakco getMapgakcoById(Long mapgakcoId) {
    return mapgakcoRepository.findByIdAndStatusNot(mapgakcoId, MapgakcoStatus.DELETED)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco for id = %d", mapgakcoId),
        ErrorCode.MAPGAKCO_NOT_FOUND));
  }

  /**
   * 삭제 되지 않은 Comment 하나 조회
   */
  public MapgakcoComment getPostedCommentById(Long commentId) {
    return commentRepository.findByIdAndStatusNot(commentId, MapgakcoCommentStatus.DELETED)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco comment for id = %d.", commentId),
        ErrorCode.MAPGAKCO_COMMENT_NOT_FOUND));
  }

  /**
   * Status상관없이 Comment 하나 조회
   */
  public MapgakcoComment getCommentById(Long commentId) {
    return commentRepository.findById(commentId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco comment for id = %d.", commentId),
        ErrorCode.MAPGAKCO_COMMENT_NOT_FOUND));
  }

  public MapgakcoApplicant getApplicantById(Long applicantId) {
    return applicantRepository.findById(applicantId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco applicant for id = %d.", applicantId),
        ErrorCode.MAPGAKCO_APPLICANT_NOT_FOUND));
  }

  public MapgakcoApplicant getApplicantByMapgakcoAndUser(Mapgakco mapgakco, User user) {
    return applicantRepository.findByMapgakcoAndUser(mapgakco, user)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco applicant for mapgakco id = %d and user id = %d.",
          mapgakco.getId(), user.getId()),
        ErrorCode.MAPGAKCO_APPLICANT_NOT_FOUND));
  }

  public List<Mapgakco> getAllMapgakco() {
    return mapgakcoRepository.getByStatusNot(MapgakcoStatus.DELETED);
  }

  public List<MapgakcoApplicant> getAllApplicantByMapgakcoWithUser(Mapgakco mapgakco) {
    return applicantRepository.getByMapgakcoWithUser(mapgakco);
  }

  /**
   * Status상관없이 Mapgakco에 대한 Parent Comment 조회
   */
  public List<MapgakcoComment> getAllParentCommentByMapgakco(Mapgakco mapgakco) {
    return commentRepository.getByMapgakcoAndParentIsNull(mapgakco);
  }

  public List<MapgakcoComment> getAllParentCommentByMapgakcoWithUser(Mapgakco mapgakco) {
    return commentRepository.getParentByMapgakcoWithUser(mapgakco);
  }

  /**
   * Status상관없이 Mapgakco에 대한 Child Comment 조회
   */
  public List<MapgakcoComment> getAllChildCommentByParent(MapgakcoComment comment) {
    return commentRepository.getByParent(comment);
  }

  public List<MapgakcoComment> getAllChildCommentByParentWithUser(MapgakcoComment comment) {
    return commentRepository.getChildByParentWithUser(comment);
  }

  public List<SimpleMapgakcoInfoDto> getAllMapgakcoInfoHostedBy(User host) {
    return mapgakcoRepository.findMapgakcosHostedBy(host).stream()
        .map(mapgakcoConverter::toMapgakcoInfo)
        .collect(Collectors.toList());
  }

  public List<SimpleMapgakcoInfoDto> getAllMapgakcoInfoAppliedBy(User applicant) {
    return mapgakcoRepository.findMapgakcosAppliedBy(applicant).stream()
        .map(mapgakcoConverter::toMapgakcoInfo)
        .collect(Collectors.toList());
  }

  public User getUserById(Long userId) {
    return userRetrieveService.getUser(userId);
  }

}
