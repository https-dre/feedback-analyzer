package br.httpsdre.feedback_analyzer.models;

import br.httpsdre.feedback_analyzer.dtos.CreateWebhookRequest;
import com.github.f4b6a3.uuid.UuidCreator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "webhooks")
@Getter
@Setter
@AllArgsConstructor
public class Webhook {
  @Id
  private UUID id;
  private String url;
  private String token;
  @Column(name = "tenant_id")
  private String tenantId;

  public Webhook(String url, String tenantId, String token) {
    this.id = UuidCreator.getTimeOrderedEpoch();
    this.url = url;
    this.token = token;
    this.tenantId = tenantId;
  }

  public Webhook(CreateWebhookRequest request) {
    this.id = UuidCreator.getTimeOrderedEpoch();
    this.url = request.url();
    this.tenantId = request.tenant_id();
    this.token = request.token();
  }
}
