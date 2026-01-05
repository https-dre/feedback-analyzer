package br.httpsdre.feedback_analyzer.dtos;

import br.httpsdre.feedback_analyzer.models.Webhook;

import java.util.UUID;

public record WebhookDetailsResponse(
        UUID id,
        String url,
        String token,
        String tenantId

) {
  public WebhookDetailsResponse(Webhook webHook) {
    this(webHook.getId(), webHook.getUrl(), webHook.getToken(), webHook.getTenantId());
  }
}
