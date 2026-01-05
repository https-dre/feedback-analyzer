package br.httpsdre.feedback_analyzer.batch.listeners;

import br.httpsdre.feedback_analyzer.infra.webhook.WebhookClient;
import br.httpsdre.feedback_analyzer.infra.webhook.WebhookPayload;
import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.models.Webhook;
import br.httpsdre.feedback_analyzer.repositories.BatchRepository;
import br.httpsdre.feedback_analyzer.repositories.FeedbackRepository;
import br.httpsdre.feedback_analyzer.repositories.WebhookRepository;
import br.httpsdre.feedback_analyzer.types.AnalysisStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchCompletionListener implements JobExecutionListener {
  private final BatchRepository batchRepository;
  private final FeedbackRepository feedbackRepository;
  private final WebhookRepository webhookRepository;
  private final WebhookClient webhookClient;

  @Override
  public void afterJob(JobExecution jobExecution) {
    String batchIdStr = jobExecution.getJobParameters().getString("batchId");
    if (batchIdStr == null) {
      log.warn("Job finished without Batch ID");
      return;
    }
    UUID batchId = UUID.fromString(batchIdStr);

    if (jobExecution.getStatus() != BatchStatus.COMPLETED) {
      log.error("Job Fail! Status: {}", jobExecution.getStatus());
    }

    log.info("Job Finished Successfuly with Batch ID: {}", batchId);

    Batch batch = batchRepository.findById(batchId).orElseThrow();
    batch.setStatus(AnalysisStatus.COMPLETED);
    batchRepository.save(batch);

    List<Webhook> tenantWebhooks = this.webhookRepository.findByTenantId(batch.getTenantId());
    if(tenantWebhooks.isEmpty()) {
      log.info("No webhooks for Batch with ID: {}", batch.getId());
      return;
    }

    // fetch the count of completed and failed feedbacks
    List<Object[]> allCounts = this.feedbackRepository.countAllByStatusByBatch(batch);
    Map<AnalysisStatus, Long> countsMap = allCounts.stream()
            .collect(Collectors.toMap(
                    row -> (AnalysisStatus) row[0],
                    row -> (Long) row[1])
            );

    WebhookPayload payload = new WebhookPayload("BATCH_COMPLETED", batch.getId().toString(), "COMPLETED",
            countsMap.getOrDefault(AnalysisStatus.COMPLETED, 0L), countsMap.getOrDefault(AnalysisStatus.FAILED, 0L));

    log.info("Sending webhooks for Batch with ID: {}", batch.getId());
    tenantWebhooks.forEach(w -> {
      this.webhookClient.sendWebhook(w.getUrl(), payload, w.getToken());
    });
  }
}
