package br.httpsdre.feedback_analyzer.application;

import br.httpsdre.feedback_analyzer.exceptions.NotFoundException;
import br.httpsdre.feedback_analyzer.exceptions.ValidationException;
import br.httpsdre.feedback_analyzer.models.WebHook;
import br.httpsdre.feedback_analyzer.repositories.WebHookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WebHookService {
  private final WebHookRepository repository;

  @Transactional
  public WebHook createWebHook(String url, String tenantId, String token) {
    Optional<WebHook> webHookWithUrl = this.repository.findByUrl(url);
    if(webHookWithUrl.isPresent()) {
      throw new ValidationException("Url already exists.");
    }
    WebHook webhook = new WebHook(url, tenantId, token);
    this.repository.save(webhook);
    return webhook;
  }

  public List<WebHook> getWebHookByTenant(String tenantId) {
    return this.repository.findByTenantId(tenantId);
  }

  public WebHook getWebHookById(String id) {
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
