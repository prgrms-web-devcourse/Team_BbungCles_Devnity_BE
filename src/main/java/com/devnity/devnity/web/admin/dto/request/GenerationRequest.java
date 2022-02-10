package com.devnity.devnity.web.admin.dto.request;

import com.devnity.devnity.domain.user.entity.Generation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest {

  Long id;
  int sequence;

  public GenerationRequest(int sequence) {
    this.sequence = sequence;
  }

  public Generation to() {
    return new Generation(sequence);
  }

}
