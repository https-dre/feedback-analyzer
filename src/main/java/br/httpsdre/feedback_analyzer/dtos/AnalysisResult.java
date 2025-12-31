package br.httpsdre.feedback_analyzer.dtos;

import br.httpsdre.feedback_analyzer.types.Category;
import br.httpsdre.feedback_analyzer.types.Sentiment;

public record AnalysisResult(
        Category category,
        Sentiment sentiment,
        String summary
) {
}
