package br.httpsdre.feedback_analyzer.batch.processors;

import br.httpsdre.feedback_analyzer.adapters.FeedbackAnalysisGateway;
import br.httpsdre.feedback_analyzer.config.FeedbackBatchProperties;
import br.httpsdre.feedback_analyzer.dtos.AnalysisResult;
import br.httpsdre.feedback_analyzer.models.Feedback;
import br.httpsdre.feedback_analyzer.types.AnalysisStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackAnalysisProcessor implements ItemProcessor<Feedback, Feedback> {
  private final FeedbackAnalysisGateway analysisGateway;
  private final FeedbackBatchProperties properties;

  @Override
  public Feedback process(Feedback item) throws Exception {
    log.info("Processing feedback with ID: {}", item.getId());
    try {
      AnalysisResult result = analysisGateway.analyze(item.getOriginalBody());
      item.setCategory(result.category());
      item.setSentiment(result.sentiment());
      item.setSummary(result.summary());
      item.setStatus(AnalysisStatus.COMPLETED);
    } catch (Exception e) {
      item.setStatus(AnalysisStatus.FAILED);
      String msg = e.getMessage() != null ? e.getMessage() : e.toString();
      if(msg.length() > 4000) msg = msg.substring(0, 4000);
      item.setErrorMessage(msg);
    }

    try {
      Thread.sleep(this.properties.getSleepDuration());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return item;
  }
}
