package br.httpsdre.feedback_analyzer.batch.listeners;

import br.httpsdre.feedback_analyzer.models.Batch;
import br.httpsdre.feedback_analyzer.repositories.BatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Slf4j
public class BatchCompletionListener implements JobExecutionListener {
  @Autowired
  private BatchRepository batchRepository;

  @Override
  public void afterJob(JobExecution jobExecution) {
    String batchIdStr = jobExecution.getJobParameters().getString("batchId");
    UUID batchId = UUID.fromString(batchIdStr);

    if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("JOB FINALIZADO COM SUCESSO! Batch ID: {}", batchId);

      Batch batch = batchRepository.findById(batchId).orElseThrow();
      batch.setStatus(br.httpsdre.feedback_analyzer.types.BatchStatus.COMPLETED);
      batchRepository.save(batch);

      log.info("Enviando WebHook para o cliente...");
    } else {
      log.error("JOB FALHOU! Status: {}", jobExecution.getStatus());
    }
  }
}
