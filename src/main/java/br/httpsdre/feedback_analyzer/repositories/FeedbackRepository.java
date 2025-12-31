package br.httpsdre.feedback_analyzer.repositories;

import br.httpsdre.feedback_analyzer.models.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
  Page<Feedback> findByBatchId(UUID batchId, Pageable pageable);
}
