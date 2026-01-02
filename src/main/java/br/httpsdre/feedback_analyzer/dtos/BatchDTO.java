package br.httpsdre.feedback_analyzer.dtos;

import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.types.AnalysisStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BatchDTO(
        String id,
        String tenantId,
        AnalysisStatus status,
        LocalDateTime createdAt,
        LocalDateTime completedAt,
        List<FeedbackDetailsResponse> feedbacks

) {
  public BatchDTO(Batch model) {
    List<FeedbackDetailsResponse> feedbacksDTOs = model.getFeedbacks().stream()
            .map(FeedbackDetailsResponse::new)
            .collect(Collectors.toList());
    this(model.getId().toString(), model.getTenantId(),
            model.getStatus(), model.getCreatedAt(),
            model.getCompletedAt(), feedbacksDTOs);
  }
}
