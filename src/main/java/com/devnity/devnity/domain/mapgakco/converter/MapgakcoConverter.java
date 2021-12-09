package com.devnity.devnity.domain.mapgakco.converter;

import com.devnity.devnity.domain.mapgakco.dto.mapgakco.MapgakcoCreateRequest;
import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
      .meetingAt(request.getMeetingDateTime())
      .user(user)
      .build();
  }

}