package br.httpsdre.feedback_analyzer.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateBatchDTO(
        @NotBlank String tenantId,
        @NotEmpty
        @Size(max = 100)
        List<String> feedbacks
) {
}
