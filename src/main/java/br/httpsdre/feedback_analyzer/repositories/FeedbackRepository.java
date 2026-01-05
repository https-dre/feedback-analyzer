package br.httpsdre.feedback_analyzer.repositories;

import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.models.Feedback;
import br.httpsdre.feedback_analyzer.types.AnalysisStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
  Page<Feedback> findByBatchId(UUID batchId, Pageable pageable);
  Long countByStatusAndBatch(AnalysisStatus status, Batch batch);
  @Query("SELECT f.status, COUNT(f) FROM Feedback f WHERE f.batch = :batch GROUP BY f.status")
  List<Object[]> countAllByStatusByBatch(Batch batch);
}
