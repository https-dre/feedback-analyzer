package br.httpsdre.feedback_analyzer.application;

import br.httpsdre.feedback_analyzer.core.CreateBatchUseCase;
import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.models.Feedback;
import br.httpsdre.feedback_analyzer.repositories.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateBatchService implements CreateBatchUseCase {
  @Autowired
  private BatchRepository repository;

  @Override
  public UUID execute(String tenantId, List<String> feedbackBodies) {
    List<Feedback> feedbacks = feedbackBodies.stream()
            .map(Feedback::new)
            .collect(Collectors.toList());
    Batch batch = new Batch(tenantId, feedbacks);
    this.repository.save(batch);
    return batch.getId();
  }
}
