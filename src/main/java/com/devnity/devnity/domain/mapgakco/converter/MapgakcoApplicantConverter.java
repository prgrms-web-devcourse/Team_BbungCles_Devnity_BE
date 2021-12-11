package com.devnity.devnity.domain.mapgakco.converter;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import com.devnity.devnity.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MapgakcoApplicantConverter {

  public MapgakcoApplicant toApplicant(Mapgakco mapgakco, User user) {
    return MapgakcoApplicant.builder()
      .mapgakco(mapgakco)
      .user(user)
      .build();
  }

}
