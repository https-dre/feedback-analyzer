package br.httpsdre.feedback_analyzer.dtos;

import br.httpsdre.feedback_analyzer.models.Feedback;
import br.httpsdre.feedback_analyzer.types.Category;
import br.httpsdre.feedback_analyzer.types.Sentiment;

public record FeedbackDetailsResponse(
        String id,
        String originalBody,
        Category category,
        Sentiment sentiment,
        String summary,
        boolean isAnalyzed

) {
  public FeedbackDetailsResponse(Feedback model) {
    this(model.getId().toString(), model.getOriginalBody(),
            model.getCategory(), model.getSentiment(),
            model.getSummary(), model.isAnalyzed());
  }
}
