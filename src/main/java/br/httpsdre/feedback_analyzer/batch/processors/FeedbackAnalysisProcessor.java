package br.httpsdre.feedback_analyzer.batch.processors;

import br.httpsdre.feedback_analyzer.adapters.FeedbackAnalysisGateway;
import br.httpsdre.feedback_analyzer.dtos.AnalysisResult;
import br.httpsdre.feedback_analyzer.models.Feedback;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackAnalysisProcessor implements ItemProcessor<Feedback, Feedback> {
  private final FeedbackAnalysisGateway analysisGateway;

  @Override
  public Feedback process(Feedback item) throws Exception {
    log.info("Processing feedback with ID: {}", item.getId());

    AnalysisResult result = analysisGateway.analyze(item.getOriginalBody());
    item.setCategory(result.category());
    item.setSentiment(result.sentiment());
    item.setAnalyzed(true);
    return item;
  }
}
