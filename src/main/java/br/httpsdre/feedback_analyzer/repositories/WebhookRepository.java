package br.httpsdre.feedback_analyzer.repositories;

import br.httpsdre.feedback_analyzer.models.Webhook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WebhookRepository extends JpaRepository<Webhook, UUID> {
  List<Webhook> findByTenantId(String tenantId);
  Optional<Webhook> findByUrl(String url);
}
