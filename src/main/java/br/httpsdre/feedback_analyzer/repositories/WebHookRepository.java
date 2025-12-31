package br.httpsdre.feedback_analyzer.repositories;

import br.httpsdre.feedback_analyzer.models.WebHook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WebHookRepository extends JpaRepository<WebHook, UUID> {
  List<WebHook> findByTenantId(String tenantId);
  Optional<WebHook> findByUrl(String url);
}
