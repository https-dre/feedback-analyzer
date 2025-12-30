package br.httpsdre.feedback_analyzer.core;

import java.util.List;
import java.util.UUID;

public interface CreateBatchUseCase {
  UUID execute(String tenantId, List<String> feedbackBodies);
}
