package com.devnity.devnity.domain.mapgakco.repository;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.user.entity.User;
import java.util.List;

public interface MapgakcoCustomRepository {

  List<Mapgakco> findMapgakcosHostedBy(User user);
  List<Mapgakco> findMapgakcosAppliedBy(User user);
}
