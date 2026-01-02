package br.httpsdre.feedback_analyzer.dtos;

import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.types.AnalysisStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record BatchSummaryResponse(
        String id,
        String tenantId,
        AnalysisStatus status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        Long feedbackCount

) {
  public BatchSummaryResponse(Batch model, Long feedbackCount) {
    this(model.getId().toString(), model.getTenantId(), model.getStatus(),
            model.getCreatedAt(), model.getCompletedAt(), feedbackCount);
  }

  public BatchSummaryResponse(
          UUID id,
          String tenantId,
          AnalysisStatus status,
          LocalDateTime createdAt,
          LocalDateTime completedAt,
          Long feedbackCount // Banco devolve Long no COUNT
  ) {
    this(
            id != null ? id.toString() : null,
            tenantId,
            status,
            createdAt,
            completedAt,
            (feedbackCount != null ? feedbackCount : 0)
    );
  }
}
