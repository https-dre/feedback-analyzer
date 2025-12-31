package br.httpsdre.feedback_analyzer.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateWebhookRequest(
        @NotBlank String url,
        String token,
        @NotBlank String tenant_id
) {
}
