package com.devnity.devnity.domain.admin.controller.dto;

import com.devnity.devnity.domain.user.entity.Generation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerationRequest {

    Long id;
    int sequence;

    public GenerationRequest(int sequence) { this.sequence = sequence; }

    public Generation to() { return new Generation(sequence); }

}
