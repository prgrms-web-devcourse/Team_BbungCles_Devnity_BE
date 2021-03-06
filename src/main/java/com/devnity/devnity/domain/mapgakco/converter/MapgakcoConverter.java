package com.devnity.devnity.domain.mapgakco.converter;

import com.devnity.devnity.web.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.web.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.web.mapgakco.dto.mapgakco.response.MapgakcoResponse;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.web.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MapgakcoConverter {
  public Mapgakco toMapgakco(User user, MapgakcoCreateRequest request) {
    return Mapgakco.builder()
      .title(request.getTitle())
      .applicantLimit(request.getApplicantLimit())
      .content(request.getContent())
      .location(request.getLocation())
      .latitude(request.getLatitude())
      .longitude(request.getLongitude())
      .meetingAt(request.getMeetingAt())
      .user(user)
      .build();
  }

  public SimpleMapgakcoInfoDto toMapgakcoInfo(Mapgakco mapgakco) {
    return SimpleMapgakcoInfoDto.builder()
      .mapgakcoId(mapgakco.getId())
      .status(mapgakco.getStatus())
      .title(mapgakco.getTitle())
      .location(mapgakco.getLocation())
      .meetingAt(mapgakco.getMeetingAt())
      .latitude(mapgakco.getLatitude())
      .longitude(mapgakco.getLongitude())
      .applicantLimit(mapgakco.getApplicantLimit())
      .applicantCount(mapgakco.getApplicantCount())
      .createdAt(mapgakco.getCreatedAt())
      .author(SimpleUserInfoDto.of(mapgakco.getUser()))
      .build();
  }

  public MapgakcoResponse toMapgakcoResponse(Mapgakco mapgakco) {
    return MapgakcoResponse.builder()
      .mapgakcoId(mapgakco.getId())
      .applicantLimit(mapgakco.getApplicantLimit())
      .applicantCount(mapgakco.getApplicantCount())
      .status(mapgakco.getStatus())
      .title(mapgakco.getTitle())
      .content(mapgakco.getContent())
      .location(mapgakco.getLocation())
      .latitude(mapgakco.getLatitude())
      .longitude(mapgakco.getLongitude())
      .meetingAt(mapgakco.getMeetingAt())
      .createdAt(mapgakco.getCreatedAt())
      .modifiedAt(mapgakco.getModifiedAt())
      .build();
  }

}