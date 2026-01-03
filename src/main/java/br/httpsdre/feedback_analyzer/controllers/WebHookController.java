package br.httpsdre.feedback_analyzer.controllers;

import br.httpsdre.feedback_analyzer.application.WebHookService;
import br.httpsdre.feedback_analyzer.dtos.CreateWebhookRequest;
import br.httpsdre.feedback_analyzer.dtos.WebHookDetailsResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/webhooks")
@Tag(name = "WebHooks", description = "Endpoints para gerenciar quantos WebHooks cada Tenant possui")
public class WebHookController {
  @Autowired
  private WebHookService service;

  @PostMapping
  public ResponseEntity<WebHookDetailsResponse>
    createWebHook(@RequestBody CreateWebhookRequest request) {
    var created = this.service.createWebHook(request.url(), request.tenant_id(), request.token());
    return ResponseEntity.status(201).body(new WebHookDetailsResponse(created));
  }

  @GetMapping("/tenant/{tenantId}")
  public ResponseEntity<List<WebHookDetailsResponse>>
    getWebHooksByTenant(@PathVariable String tenantId) {
    var webHooks = this.service.getWebHookByTenant(tenantId);
    return ResponseEntity.ok(webHooks.stream()
            .map(WebHookDetailsResponse::new).toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<WebHookDetailsResponse>
    getById(@PathVariable String id) {
    var webhook = this.service.getWebHookById(id);
    return ResponseEntity.ok(new WebHookDetailsResponse(webhook));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable String id) {
    this.service.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
