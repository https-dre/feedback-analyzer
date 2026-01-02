package br.httpsdre.feedback_analyzer.application;

import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.models.Feedback;
import br.httpsdre.feedback_analyzer.repositories.BatchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class BatchService {
  private BatchRepository repository;
  private final JobLauncher jobLauncher;
  private final Job feedbackAnalysisJob;

  public UUID execute(String tenantId, List<String> feedbackBodies) {
    Batch batch = new Batch(tenantId, null);
    List<Feedback> feedbacks = feedbackBodies.stream()
            .map(s -> new Feedback(s, batch))
            .collect(Collectors.toList());
    batch.setFeedbacks(feedbacks);

    this.repository.save(batch);
    CompletableFuture.runAsync(() -> {
      try {
        log.info("Starting Job for Batch ID: {}", batch.getId());
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("batchId", batch.getId().toString())
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        this.jobLauncher.run(this.feedbackAnalysisJob, jobParameters);
      } catch (Exception e) {
        log.error("Error while starting Job for Batch ID: {}", batch.getId());
      }
    });
    return batch.getId();
  }
}
