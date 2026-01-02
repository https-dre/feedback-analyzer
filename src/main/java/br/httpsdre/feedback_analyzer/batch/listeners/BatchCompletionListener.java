package br.httpsdre.feedback_analyzer.batch.listeners;

import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.repositories.BatchRepository;
import br.httpsdre.feedback_analyzer.types.AnalysisStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BatchCompletionListener implements JobExecutionListener {
  private BatchRepository batchRepository;

  @Override
  public void afterJob(JobExecution jobExecution) {
    String batchIdStr = jobExecution.getJobParameters().getString("batchId");
    if (batchIdStr == null) {
      log.warn("Job finished without Batch ID");
      return;
    }
    UUID batchId = UUID.fromString(batchIdStr);

    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("Job Finished Successfuly with Batch ID: {}", batchId);

      Batch batch = batchRepository.findById(batchId).orElseThrow();
      batch.setStatus(AnalysisStatus.COMPLETED);
      batchRepository.save(batch);

      log.info("Sending WebHook...");
    } else {
      log.error("Job Fail! Status: {}", jobExecution.getStatus());
    }
  }
}
