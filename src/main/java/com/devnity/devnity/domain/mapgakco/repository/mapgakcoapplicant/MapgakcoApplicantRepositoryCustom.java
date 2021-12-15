package com.devnity.devnity.domain.mapgakco.repository.mapgakcoapplicant;

import com.devnity.devnity.domain.mapgakco.entity.Mapgakco;
import com.devnity.devnity.domain.mapgakco.entity.MapgakcoApplicant;
import java.util.List;

public interface MapgakcoApplicantRepositoryCustom {

  List<MapgakcoApplicant> getByMapgakcoWithUser(Mapgakco mapgakco);

}
