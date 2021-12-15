package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoComment;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoApplicantRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoCommentRepository;
import com.devnity.devnity.domain.mapgakco.repository.MapgakcoRepository;
import com.devnity.devnity.domain.user.entity.User;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
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
    return mapgakcoRepository.findById(mapgakcoId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("There is no mapgakco for id = %d", mapgakcoId),
        ErrorCode.MAPGAKCO_NOT_FOUND));
  }

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

  public User getUserById(Long userId) {
    return userRetrieveService.getUser(userId);
  }

}
