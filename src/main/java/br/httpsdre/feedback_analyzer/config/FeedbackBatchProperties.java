package br.httpsdre.feedback_analyzer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "feedback.batch")
public class FeedbackBatchProperties {
  private int chunkSize = 1;
  private long sleepDuration = 15000;
}
