package br.httpsdre.feedback_analyzer.controllers;

import br.httpsdre.feedback_analyzer.application.WebhookService;
import br.httpsdre.feedback_analyzer.dtos.CreateWebhookRequest;
import br.httpsdre.feedback_analyzer.dtos.WebhookDetailsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webhooks")
@Tag(name = "WebHooks", description = "Endpoints para gerenciar quantos WebHooks cada Tenant possui")
public class WebhookController {
  @Autowired
  private WebhookService service;

  @PostMapping
  public ResponseEntity<WebhookDetailsResponse>
    createWebHook(@RequestBody CreateWebhookRequest request) {
    var created = this.service.createWebHook(request.url(), request.tenant_id(), request.token());
    return ResponseEntity.status(201).body(new WebhookDetailsResponse(created));
  }

  @GetMapping("/tenant/{tenantId}")
  public ResponseEntity<List<WebhookDetailsResponse>>
    getWebHooksByTenant(@PathVariable String tenantId) {
    var webHooks = this.service.getWebHookByTenant(tenantId);
    return ResponseEntity.ok(webHooks.stream()
            .map(WebhookDetailsResponse::new).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<WebhookDetailsResponse>
    getById(@PathVariable String id) {
    var webhook = this.service.getWebHookById(id);
    return ResponseEntity.ok(new WebhookDetailsResponse(webhook));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable String id) {
    this.service.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
