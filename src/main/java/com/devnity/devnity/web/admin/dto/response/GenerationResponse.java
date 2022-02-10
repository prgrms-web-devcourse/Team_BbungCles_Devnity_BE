package com.devnity.devnity.web.admin.dto.response;

import com.devnity.devnity.domain.user.entity.Generation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerationResponse {

  Long generationId;
  int sequence;

  public static GenerationResponse from(Generation generation) {
    return new GenerationResponse(generation.getId(), generation.getSequence());
  }
}
