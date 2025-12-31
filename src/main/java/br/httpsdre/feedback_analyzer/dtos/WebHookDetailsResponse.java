package br.httpsdre.feedback_analyzer.dtos;

import br.httpsdre.feedback_analyzer.models.WebHook;

import java.util.UUID;

public record WebHookDetailsResponse(
        UUID id,
        String url,
        String token,
        String tenantId

) {
  public WebHookDetailsResponse(WebHook webHook) {
    this(webHook.getId(), webHook.getUrl(), webHook.getToken(), webHook.getTenantId());
  }
}
