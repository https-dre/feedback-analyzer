package br.httpsdre.feedback_analyzer.repositories;

import br.httpsdre.feedback_analyzer.dtos.BatchSummaryResponse;
import br.httpsdre.feedback_analyzer.models.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BatchRepository extends JpaRepository<Batch, UUID> {
  List<Batch> findByTenantId(String tenantId);

  @Query("""
    SELECT new br.httpsdre.feedback_analyzer.dtos.BatchSummaryResponse(
        b.id,
        b.tenantId,
        b.status,
        b.createdAt,
        b.completedAt,
        COUNT(f)
    )
    FROM Batch b
    LEFT JOIN b.feedbacks f
    WHERE b.tenantId = :tenantId
    GROUP BY b.id, b.tenantId, b.status, b.createdAt, b.completedAt
    """)
  List<BatchSummaryResponse> findSummariesByTenantId(String tenantId);
}
