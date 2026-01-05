package br.httpsdre.feedback_analyzer.application;

import br.httpsdre.feedback_analyzer.exceptions.NotFoundException;
import br.httpsdre.feedback_analyzer.exceptions.ValidationException;
import br.httpsdre.feedback_analyzer.models.Webhook;
import br.httpsdre.feedback_analyzer.repositories.WebhookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WebhookService {
  private final WebhookRepository repository;

  @Transactional
  public Webhook createWebHook(String url, String tenantId, String token) {
    Optional<Webhook> webHookWithUrl = this.repository.findByUrl(url);
    if(webHookWithUrl.isPresent()) {
      throw new ValidationException("Url already exists.");
    }
    Webhook webhook = new Webhook(url, tenantId, token);
    this.repository.save(webhook);
    return webhook;
  }

  public List<Webhook> getWebHookByTenant(String tenantId) {
    return this.repository.findByTenantId(tenantId);
  }

  public Webhook getWebHookById(String id) {
    var search = this.repository.findById(UUID.fromString(id));
    if(search.isEmpty()) {
      throw new NotFoundException("WebHook not found");
    }
    return search.get();
  }

  @Transactional
  public void deleteById(String id) {
    var search = this.repository.findById(UUID.fromString(id));
    if(search.isEmpty()) {
      throw new NotFoundException("WebHook not found");
    }
    this.repository.deleteById(search.get().getId());
  }
}
