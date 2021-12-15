package com.devnity.devnity.domain.mapgakco.service;

import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoDetailResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.response.MapgakcoResponse;
import com.devnity.devnity.domain.mapgakco.dto.mapgakcocomment.response.MapgakcoCommentResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.service.mapgakco.MapgakcoService;
import com.devnity.devnity.domain.mapgakco.service.mapgakcoapplicant.MapgakcoApplicantService;
import com.devnity.devnity.domain.mapgakco.service.mapgakcocomment.MapgakcoCommentService;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.service.UserRetrieveService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapgakcoDomainService {

  private final MapgakcoService mapgakcoService;
  private final MapgakcoApplicantService applicantService;
  private final MapgakcoCommentService commentService;
  private final MapgakcoRetrieveService mapgakcoRetrieveService;
  private final UserRetrieveService userRetrieveService;

  public MapgakcoDetailResponse getDetail(Long mapgakcoId) {
    Mapgakco mapgakco = mapgakcoRetrieveService.getMapgakcoById(mapgakcoId);

    MapgakcoResponse mapgakcoResponse = mapgakcoService.getMapgakco(mapgakco);
    SimpleUserInfoDto writerResponse = userRetrieveService.getSimpleUserInfo(
      mapgakco.getUser().getId());
    List<SimpleUserInfoDto> applicantsResponses = applicantService.getAllApplicantByMapgakco(
      mapgakco);
    List<MapgakcoCommentResponse> commentResponses = commentService.getAllCommentByMapgakco(
      mapgakco);

    return MapgakcoDetailResponse.of(mapgakcoResponse, writerResponse, applicantsResponses,
      commentResponses);
  }
}
