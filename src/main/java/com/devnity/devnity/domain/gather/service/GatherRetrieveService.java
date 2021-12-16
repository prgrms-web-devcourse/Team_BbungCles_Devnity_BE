package com.devnity.devnity.domain.gather.service;

import com.devnity.devnity.common.error.exception.EntityNotFoundException;
import com.devnity.devnity.common.error.exception.ErrorCode;
import com.devnity.devnity.domain.gather.dto.SimpleGatherInfoDto;
import com.devnity.devnity.domain.gather.entity.Gather;
import com.devnity.devnity.domain.gather.entity.GatherApplicant;
import com.devnity.devnity.domain.gather.entity.GatherComment;
import com.devnity.devnity.domain.gather.repository.GatherApplicantRepository;
import com.devnity.devnity.domain.gather.repository.GatherCommentRepository;
import com.devnity.devnity.domain.gather.repository.GatherRepository;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class GatherRetrieveService {

  private final GatherRepository gatherRepository;
  private final GatherCommentRepository commentRepository;
  private final GatherApplicantRepository applicantRepository;

  public Gather getGather(Long gatherId) {
    return gatherRepository.findById(gatherId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("해당 Gather 엔티티를 찾을 수 없습니다. (id : %d)", gatherId),
        ErrorCode.GATHER_NOT_FOUND
      ));
  }

  public GatherComment getComment(Long commentId) {
    return commentRepository.findById(commentId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("해당 GatherComment 엔티티를 찾을 수 없습니다. (id : %d)", commentId),
        ErrorCode.GATHER_COMMENT_NOT_FOUND
      ));
  }

  public GatherApplicant getApplicant(Long userId, Long gatherId) {
    return applicantRepository.findByUserIdAndGatherId(userId, gatherId)
      .orElseThrow(() -> new EntityNotFoundException(
        String.format("해당 GatherApplicant 엔티티를 찾을 수 없습니다. (userId : %d, gatherId : %d)", userId, gatherId),
        ErrorCode.GATHER_APPLICANT_NOT_FOUND
      ));
  }

  public boolean getIsApplied(Long userId, Long gatherId) {
    return applicantRepository.existsByUserIdAndGatherId(userId, gatherId);
  }

  public List<GatherComment> getComments(Gather gather, GatherComment parent) {
    return commentRepository.findByGatherAndParent(gather, parent);
  }

  public List<SimpleGatherInfoDto> getGathersHostedBy(User host) {
    return gatherRepository.findGathersHostedBy(host).stream()
      .map(SimpleGatherInfoDto::of)
      .collect(Collectors.toList());
  }

  public List<SimpleGatherInfoDto> getGathersAppliedBy(User applicant) {
    return gatherRepository.findGathersAppliedBy(applicant).stream()
      .map(SimpleGatherInfoDto::of)
      .collect(Collectors.toList());
  }

}
