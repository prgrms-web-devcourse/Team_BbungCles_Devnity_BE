package com.devnity.devnity.domain.mapgakco.converter;

import com.devnity.devnity.domain.mapgakco.dto.SimpleMapgakcoInfoDto;
import com.devnity.devnity.domain.mapgakco.dto.mapgakco.request.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.user.dto.SimpleUserInfoDto;
import com.devnity.devnity.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MapgakcoConverter {

  public Mapgakco toMapgakco(User user, MapgakcoCreateRequest request) {
    return Mapgakco.builder()
      .title(request.getTitle())
      .applicantLimit(request.getApplicantLimit())
      .deadline(request.getDeadline())
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
      .deadline(mapgakco.getDeadline())
      .meetingAt(mapgakco.getMeetingAt())
      .applicantLimit(mapgakco.getApplicantLimit())
      .applicantCount(mapgakco.getApplicantCount())
      .createdAt(mapgakco.getCreatedAt())
      .author(SimpleUserInfoDto.of(mapgakco.getUser()))
      .build();
  }
}