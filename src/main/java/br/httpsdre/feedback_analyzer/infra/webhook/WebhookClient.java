package br.httpsdre.feedback_analyzer.infra.webhook;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Async
@Component
public class WebhookClient {
  private final RestClient restClient;

  public WebhookClient(RestClient.Builder builder) {
    this.restClient = builder.build();
  }

  public void sendWebhook(String url, Object payload, String token) {
    try {
      restClient.post()
              .uri(url)
              .headers(httpHeaders -> {
                if (token != null) {
                  httpHeaders.setBearerAuth(token);
                }
              })
              .contentType(MediaType.APPLICATION_JSON)
              .body(payload)
              .retrieve()
              .toBodilessEntity();
    } catch (Exception _) {}
  }
}
